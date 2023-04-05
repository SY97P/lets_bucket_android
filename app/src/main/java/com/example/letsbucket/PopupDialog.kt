package com.example.letsbucket

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.example.letsbucket.data.ThisYearItem
import com.example.letsbucket.databinding.DialogPopupBinding
import com.example.letsbucket.util.DataUtil

class PopupDialog(
    context: Context,
    type: DataUtil.POPUP_TYPE,
    idx: Int
): Dialog(context){

    private var TAG: String = DataUtil.TAG + "PopupDialog"

    private lateinit var binding: DialogPopupBinding
//    private var popupDialogInterface: PopupDialogInterface
    private var TYPE: DataUtil.POPUP_TYPE
    private var itemIdx: Int

    init {
        this.TYPE = type
        itemIdx = idx
    }

    interface PopupDialogInterface {
        fun itemListChangedListener(isChanged: Boolean)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBinding()
    }

//    fun setOnPopupDialogListener(onPopupDialogInterface: PopupDialogInterface) {
//        this.popupDialogInterface = onPopupDialogInterface
//    }

    private fun setupBinding() = with(binding) {
        setCancelable(false)

        binding.popupCloseBtn.setOnClickListener {
            dismiss()
        }

        binding.popupConfirmBtn.setOnClickListener {
            Log.d(TAG, binding.popupEditText.text!!.length.toString())

            if (!binding.popupEditText.text!!.isBlank()) {
                if (TYPE == DataUtil.POPUP_TYPE.ADD) {
                    Log.d(TAG, "ADD -> " + binding.popupEditText.text!!.toString())
                    DataUtil.thisYearBucketList.add(
                        ThisYearItem(
                            id = System.currentTimeMillis(),
                            text = binding.popupEditText.text.toString(),
                            done = false
                        )
                    )
                    Log.d(TAG, "thisyearbucklist length : " + DataUtil.thisYearBucketList.size)
                } else if (TYPE == DataUtil.POPUP_TYPE.MODIFY) {
                    Log.d(TAG, "MOD -> " + binding.popupEditText.text!!.toString())
                    if (itemIdx >= 0 && itemIdx < DataUtil.thisYearBucketList.size) {
                        val copyItem = DataUtil.thisYearBucketList.get(itemIdx)
                        DataUtil.thisYearBucketList.set(
                            itemIdx,
                            ThisYearItem(
                                id = copyItem.itemId,
                                text = binding.popupEditText.text.toString(),
                                done = copyItem.itemDone
                            )
                        )
                    } else {
                        Log.d(TAG, "itemIdx is out of range")
                    }
//                    popupDialogInterface.itemListChangedListener(true)
                } else {
                    Log.d(TAG, "Invalid POPUP_TYPE")
                }

            } else {
                Log.d(TAG, "text is blank")
            }

            DataUtil.printThisYearBucketList()

            dismiss()
        }
    }
}