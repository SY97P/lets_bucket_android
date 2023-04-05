package com.example.letsbucket

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.example.letsbucket.databinding.DialogPopupBinding
import com.example.letsbucket.util.DataUtil

class PopupDialog(context: Context): Dialog(context){

    private lateinit var binding: DialogPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBinding()
    }

    private fun setupBinding() = with(binding) {
        setCancelable(false)

        binding.popupCloseBtn.setOnClickListener {
            dismiss()
        }

        binding.popupConfirmBtn.setOnClickListener {
            Log.d("mylog", binding.popupEditText.text!!.length.toString())
            if (!binding.popupEditText.text!!.isBlank()) {
                Log.d("mylog", binding.popupEditText.text!!.toString())
                DataUtil.thisYearBucketList.add(
                    ThisYearItem(
                        id = System.currentTimeMillis(),
                        text = binding.popupEditText.text.toString(),
                        done = false
                    )
                )
                Log.d("mylog", "thisyearbucklist length : " + DataUtil.thisYearBucketList.size)
            }
            dismiss()
        }
    }
}