package com.bucket.letsbucket.fragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.bucket.letsbucket.data.BucketItem
import com.bucket.letsbucket.databinding.DialogAddPopupBinding
import com.bucket.letsbucket.db.LifeBucketDB
import com.bucket.letsbucket.db.ThisYearBucketDB
import com.bucket.letsbucket.util.DataUtil
import com.bucket.letsbucket.util.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AddPopupDialog(
    context: Context,
    from: DataUtil.FROM_TYPE,
    type: Int?
) : Dialog(context) {

    private var TAG: String = "AddPopupDialog"

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
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binding.root)

        if (fromType == DataUtil.FROM_TYPE.LIFE && lifeType == null) {
            LogUtil.d(TAG, "lifetype is null -> invalid access")
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
                LogUtil.d(TAG, "text is blank -> Adding item denied")
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
    }

    private fun addToList() {
        when (fromType) {
            DataUtil.FROM_TYPE.THIS_YEAR -> {
                addedBucketItem = BucketItem(
                    id = System.currentTimeMillis(),
                    text = binding.popupEditText.text.toString(),
                    done = false,
                    lifetype = null,
                    date = "",
                    uri = ""
                )
                DataUtil.THIS_YEAR_LIST.add(addedBucketItem)
            }
            DataUtil.FROM_TYPE.LIFE -> {
                addedBucketItem = BucketItem(
                    id = System.currentTimeMillis(),
                    text = binding.popupEditText.text.toString(),
                    done = false,
                    lifetype = lifeType,
                    date = "",
                    uri = ""
                )
                DataUtil.LIFE_LIST[lifeType!!].add(addedBucketItem)
            }
            else -> {}
        }
    }
}