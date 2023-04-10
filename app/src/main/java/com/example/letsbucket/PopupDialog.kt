package com.example.letsbucket

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.letsbucket.data.BucketItem
import com.example.letsbucket.databinding.DialogPopupBinding
import com.example.letsbucket.db.LifeBucketDB
import com.example.letsbucket.db.ThisYearBucketDB
import com.example.letsbucket.util.DataUtil
import com.example.letsbucket.util.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PopupDialog(
    context: Context,
    mode: DataUtil.MODE_TYPE,
    from: DataUtil.FROM_TYPE,
    type: Int?,
    idx: Int?
) : Dialog(context) {

    private var TAG: String = "PopupDiaLogUtil"

    private lateinit var binding: DialogPopupBinding
    private lateinit var addedBucketItem: BucketItem
    private var MODE: DataUtil.MODE_TYPE
    private var FROM: DataUtil.FROM_TYPE
    private var lifeType: Int? = null
    private var itemIdx: Int? = null

    init {
        this.MODE = mode
        this.FROM = from
        this.lifeType = type
        itemIdx = idx
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (FROM == DataUtil.FROM_TYPE.LIFE && lifeType == null) {
            LogUtil.d(TAG, "Invalid Popup inflate: FROM_TYPE.LIFE & lifetype = null")
            onBackPressed()
        }

        setupBinding()
    }

    private fun setupBinding() = with(binding) {
        setCancelable(false)

        // 닫기 버튼
        binding.popupCloseBtn.setOnClickListener {
            dismiss()
        }

        // 확인 버튼
        binding.popupConfirmBtn.setOnClickListener {
            if (binding.popupEditText.text!!.isNotBlank()) {
                manupulateList()
                manupulateDB()
            } else {
                LogUtil.d(TAG, "text is blank")
            }

            dismiss()
        }
    }

    private fun manupulateDB() {
        when (MODE) {
            DataUtil.MODE_TYPE.ADD -> {
                addToDB()
            }
            DataUtil.MODE_TYPE.MODIFY -> {
                modifyToDB()
            }
            else -> {}
        }
    }

    private fun addToDB() {
        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                when (FROM) {
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
        LogUtil.d(TAG, "DB 추가")
    }

    private fun modifyToDB() {
        // 아이템 텍스트 변경 시 DB 작업
        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                when (FROM) {
                    DataUtil.FROM_TYPE.THIS_YEAR -> {
                        val modifiedItem = DataUtil.thisYearBucketList.get(itemIdx!!)
                        ThisYearBucketDB.getInstance(context)!!.thisYearBucketDao().updateText(
                            modifiedItem.itemText,
                            modifiedItem.itemId
                        )
                    }
                    DataUtil.FROM_TYPE.LIFE -> {
                        val modifiedItem = DataUtil.lifelist[lifeType!!].get(itemIdx!!)
                        LifeBucketDB.getInstance(context)!!.lifebucketDao().updateText(
                            modifiedItem.itemText,
                            modifiedItem.itemId
                        )
                    }
                    else -> {}
                }

            }.await()
            LogUtil.d(TAG, "팝업 종료 -> DB 데이터 변경 완료(수정)")
        }
    }

    private fun manupulateList() {
        when (MODE) {
            DataUtil.MODE_TYPE.ADD -> {
                LogUtil.d(TAG, "ADD -> " + binding.popupEditText.text!!.toString())
                addToList()
            }
            DataUtil.MODE_TYPE.MODIFY -> {
                LogUtil.d(TAG, "MOD -> " + binding.popupEditText.text!!.toString())
                modifyToList()
            }
            else -> {
                LogUtil.d(TAG, "Invalid MODE_TYPE")
            }
        }
    }

    private fun addToList() {
        when (FROM) {
            DataUtil.FROM_TYPE.THIS_YEAR -> {
                addedBucketItem = BucketItem(
                    id = System.currentTimeMillis(),
                    text = binding.popupEditText.text.toString(),
                    done = false,
                    lifetype = null
                )
                DataUtil.thisYearBucketList.add(addedBucketItem)

                LogUtil.d(TAG, "thisyearbucklist length : " + DataUtil.thisYearBucketList.size)
            }
            DataUtil.FROM_TYPE.LIFE -> {
                addedBucketItem = BucketItem(
                    id = System.currentTimeMillis(),
                    text = binding.popupEditText.text.toString(),
                    done = false,
                    lifetype = lifeType
                )
                DataUtil.lifelist[lifeType!!].add(addedBucketItem)

                LogUtil.d(TAG, "lifelist length : " + DataUtil.lifelist.size)
            }
            else -> {}
        }
    }

    private fun modifyToList() {
        when (FROM) {
            DataUtil.FROM_TYPE.THIS_YEAR -> {
                if (itemIdx!! >= 0 && itemIdx!! < DataUtil.thisYearBucketList.size) {
                    val copyItem = DataUtil.thisYearBucketList.get(itemIdx!!)
                    DataUtil.thisYearBucketList.set(
                        itemIdx!!,
                        BucketItem(
                            id = copyItem.itemId,
                            text = binding.popupEditText.text.toString(),
                            done = copyItem.itemDone,
                            lifetype = copyItem.lifeType
                        )
                    )
                } else {
                    LogUtil.d(TAG, "itemIdx!! is out of range")
                }
            }
            DataUtil.FROM_TYPE.LIFE -> {
                if (itemIdx!! >= 0 && itemIdx!! < DataUtil.lifelist.size) {
                    val copyItem = DataUtil.lifelist[lifeType!!].get(itemIdx!!)
                    DataUtil.lifelist[lifeType!!].set(
                        itemIdx!!,
                        BucketItem(
                            id = copyItem.itemId,
                            text = binding.popupEditText.text.toString(),
                            done = copyItem.itemDone,
                            lifetype = copyItem.lifeType
                        )
                    )
                } else {
                    LogUtil.d(TAG, "itemIdx!! is out of range")
                }
            }
            else -> {}
        }
    }
}