package com.example.letsbucket.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.letsbucket.data.BucketItem
import com.example.letsbucket.databinding.DialogAddPopupBinding
import com.example.letsbucket.db.LifeBucketDB
import com.example.letsbucket.db.ThisYearBucketDB
import com.example.letsbucket.util.DataUtil
import com.example.letsbucket.util.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AddPopupDialog(
    context: Context,
    from: DataUtil.FROM_TYPE,
    type: Int?
) : Dialog(context) {

    private var TAG: String = "PopupDiaLogUtil"

    private lateinit var binding: DialogAddPopupBinding

    private var fromType: DataUtil.FROM_TYPE
    private var lifeType: Int? = null

    private lateinit var addedBucketItem: BucketItem

    init {
        this.fromType = from
        this.lifeType = type
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogAddPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (fromType == DataUtil.FROM_TYPE.LIFE && lifeType == null) {
            LogUtil.d("lifetype is null")
            onBackPressed()
        }

        setupBinding()
    }

    /**
     * setupBinding
     *
     * 1. 닫기 버튼 동작 -> dismiss()
     * 2. 확인 버튼 동작 -> 아이템 추가
     */
    private fun setupBinding() = with(binding) {
        setCancelable(false)

        // 닫기 버튼
        binding.popupCloseBtn.setOnClickListener {
            dismiss()
        }

        // 확인 버튼
        binding.popupConfirmBtn.setOnClickListener {
            if (binding.popupEditText.text!!.isNotBlank()) {
                addToList()
                addToDB()
            } else {
                LogUtil.d("text is blank")
            }

            dismiss()
        }
    }

    private fun addToDB() {
        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                when (fromType) {
                    DataUtil.FROM_TYPE.THIS_YEAR -> {
                        ThisYearBucketDB.getInstance(context)!!.thisYearBucketDao().insert(
                            addedBucketItem.convertToThisYearEntity()
                        )
                    }
                    DataUtil.FROM_TYPE.LIFE -> {
                        LifeBucketDB.getInstance(context)!!.lifebucketDao().insert(
                            addedBucketItem.convertToLifeEntity()
                        )
                    }
                    else -> {}
                }
            }.await()
        }
        LogUtil.d("DB 추가")
    }

    private fun addToList() {
        when (fromType) {
            DataUtil.FROM_TYPE.THIS_YEAR -> {
                addedBucketItem = BucketItem(
                    id = System.currentTimeMillis(),
                    text = binding.popupEditText.text.toString(),
                    done = false,
                    lifetype = null
                )
                DataUtil.THIS_YEAR_LIST.add(addedBucketItem)

                LogUtil.d("thisyearbucklist length : " + DataUtil.THIS_YEAR_LIST.size)
            }
            DataUtil.FROM_TYPE.LIFE -> {
                addedBucketItem = BucketItem(
                    id = System.currentTimeMillis(),
                    text = binding.popupEditText.text.toString(),
                    done = false,
                    lifetype = lifeType
                )
                DataUtil.LIFE_LIST[lifeType!!].add(addedBucketItem)

                LogUtil.d("LIFE_LIST length : " + DataUtil.LIFE_LIST.size)
            }
            else -> {}
        }
    }
}