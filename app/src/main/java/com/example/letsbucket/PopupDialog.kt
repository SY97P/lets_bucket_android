package com.example.letsbucket

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.example.letsbucket.data.BucketItem
import com.example.letsbucket.databinding.DialogPopupBinding
import com.example.letsbucket.util.DataUtil

class PopupDialog(
    context: Context,
    mode: DataUtil.MODE_TYPE,
    from: DataUtil.FROM_TYPE,
    type: Int?,
    idx: Int?
): Dialog(context){

    private var TAG: String = DataUtil.TAG + "PopupDialog"

    private lateinit var binding: DialogPopupBinding
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
            Log.d(TAG, "Invalid Popup inflate: FROM_TYPE.LIFE & lifetype = null")
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
                confirmPopup()
            } else {
                Log.d(TAG, "text is blank")
            }

            dismiss()
        }
    }

    private fun confirmPopup() {
        when(MODE) {
            DataUtil.MODE_TYPE.ADD -> {
                Log.d(TAG, "ADD -> " + binding.popupEditText.text!!.toString())

                when (FROM) {
                    DataUtil.FROM_TYPE.THIS_YEAR -> {
                        DataUtil.thisYearBucketList.add(
                            BucketItem(
                                id = System.currentTimeMillis(),
                                text = binding.popupEditText.text.toString(),
                                done = false
                            )
                        )
                        Log.d(TAG, "thisyearbucklist length : " + DataUtil.thisYearBucketList.size)
                    }
                    DataUtil.FROM_TYPE.LIFE -> {
                        DataUtil.lifelist[lifeType!!].add(
                            BucketItem(
                                id = System.currentTimeMillis(),
                                text = binding.popupEditText.text.toString(),
                                done = false
                            )
                        )
                        Log.d(TAG, "lifelist length : " + DataUtil.lifelist.size)
                    }
                    else -> {  }
                }
            }
            DataUtil.MODE_TYPE.MODIFY -> {
                Log.d(TAG, "MOD -> " + binding.popupEditText.text!!.toString())

                when (FROM) {
                    DataUtil.FROM_TYPE.THIS_YEAR -> {
                        if (itemIdx!! >= 0 && itemIdx!! < DataUtil.thisYearBucketList.size) {
                            val copyItem = DataUtil.thisYearBucketList.get(itemIdx!!)
                            DataUtil.thisYearBucketList.set(
                                itemIdx!!,
                                BucketItem(
                                    id = copyItem.itemId,
                                    text = binding.popupEditText.text.toString(),
                                    done = copyItem.itemDone
                                )
                            )
                        } else { Log.d(TAG, "itemIdx!! is out of range") }
                    }
                    DataUtil.FROM_TYPE.LIFE -> {
                        if (itemIdx!! >= 0 && itemIdx!! < DataUtil.lifelist.size) {
                            val copyItem = DataUtil.lifelist[lifeType!!].get(itemIdx!!)
                            DataUtil.lifelist[lifeType!!].set(
                                itemIdx!!,
                                BucketItem(
                                    id = copyItem.itemId,
                                    text = binding.popupEditText.text.toString(),
                                    done = copyItem.itemDone
                                )
                            )
                        } else { Log.d(TAG, "itemIdx!! is out of range") }
                    }
                    else -> {  }
                }
            }
            else -> {
                Log.d(TAG, "Invalid MODE_TYPE")
            }
        }

        // 변화한 리스트 출력
        if (FROM == DataUtil.FROM_TYPE.THIS_YEAR) {
            DataUtil.printThisYearBucketList()
        } else {
            DataUtil.printLifeList(lifeType!!)
        }
    }
}