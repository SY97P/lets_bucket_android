package com.bucket.letsbucket.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import androidx.core.content.ContextCompat
import com.bucket.letsbucket.activity.DetailActivity
import com.bucket.letsbucket.data.BucketItem
import com.bucket.letsbucket.data.DetailData
import com.bucket.letsbucket.databinding.DialogAddPopupBinding
import com.bucket.letsbucket.db.LifeBucketDB
import com.bucket.letsbucket.util.DataUtil
import com.bucket.letsbucket.util.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AddPopupDialog(
    context: Context,
    type: Int?
) : Dialog(context) {

    private var TAG: String = "AddPopupDialog"

    private lateinit var binding: DialogAddPopupBinding

    private var lifeType: Int? = null

    private lateinit var addedBucketItem: BucketItem

    init {
        this.lifeType = type
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogAddPopupBinding.inflate(layoutInflater)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binding.root)

        if (lifeType == null) {
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
            if (binding.popupEditText.text!!.length in 1..15) {
                addToList()
                addToDB()
                val intent = Intent(context, DetailActivity::class.java)
                val idx = DataUtil.BUCKET_LIST[lifeType!!].lastIndex
                val data = DataUtil.BUCKET_LIST[lifeType!!].get(idx)
                intent.putExtra(
                    "DATA", DetailData(
                        data.itemId, data.itemText, data.itemDone, data.itemType,
                        data.itemDoneDate, data.itemTargetDate, data.itemUri, data.itemDetailText,
                        idx,
                    )
                )
                ContextCompat.startActivity(context, intent, null)
            } else {
                LogUtil.d(TAG, "text is blank -> Adding item denied")
                AlertUtilDialog(context, DataUtil.DIALOG_TYPE.BLANK_BUCKET).let {
                    it.build(binding.popupEditText.text.toString())
                    it.show()
                }
            }
            dismiss()
        }
    }

    private fun addToDB() {
        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                LifeBucketDB.getInstance(context)!!.lifebucketDao().insert(
                    addedBucketItem.convertToLifeEntity()
                )
            }.await()
        }
    }

    private fun addToList() {
        addedBucketItem = BucketItem(
            id = System.currentTimeMillis(),
            text = binding.popupEditText.text.toString(),
            done = false,
            lifetype = lifeType,
            doneDate = "",
            targetDate = "",
            uri = "",
            detailText = null
        )
        DataUtil.BUCKET_LIST[lifeType!!].add(addedBucketItem)
    }
}