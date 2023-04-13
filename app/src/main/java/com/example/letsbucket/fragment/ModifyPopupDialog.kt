package com.example.letsbucket.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import com.example.letsbucket.data.BucketItem
import com.example.letsbucket.databinding.DialogModifyPopupBinding
import com.example.letsbucket.db.LifeBucketDB
import com.example.letsbucket.db.ThisYearBucketDB
import com.example.letsbucket.util.DataUtil
import com.example.letsbucket.util.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.GregorianCalendar

class ModifyPopupDialog (
    context: Context,
    from: DataUtil.FROM_TYPE,
    item: BucketItem,
    idx: Int
): Dialog(context) {

    private lateinit var binding: DialogModifyPopupBinding

    private var fromType: DataUtil.FROM_TYPE
    private var item: BucketItem
    private var idx: Int
    private var date: String = ""

    init {
        this.fromType = from
        this.item = item
        this.idx = idx
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogModifyPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (fromType == DataUtil.FROM_TYPE.LIFE) {
            if (item.itemType == null) {
                LogUtil.d(" TYPE is null")
                onBackPressed()
            } else if (idx < 0 || idx >= DataUtil.LIFE_LIST[item.itemType!!].size){
                LogUtil.d("index out of range")
                onBackPressed()
            }
        } else if (fromType == DataUtil.FROM_TYPE.THIS_YEAR) {
            if (idx < 0 || idx >= DataUtil.THIS_YEAR_LIST.size) {
                LogUtil.d("index out of range")
                onBackPressed()
            }
        }

        setupBinding()
    }

    private fun setupBinding() {
        binding.let {
            // 뒤로가기 버튼
            it.buttonBack.setOnClickListener { dismiss() }

            // 확인 버튼
            it.buttonConfirm.setOnClickListener {
                modifyToList()
                modifyToDB()
            }

            // 아이템 완료 버튼
            it.bucketCheck.setOnClickListener {

            }

            // 캘린더뷰
            it.calendarLayout.setOnClickListener {
                val today = GregorianCalendar()
                DatePickerDialog(
                    context,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        this.date = "${year}/${month+1}/${dayOfMonth}"
                    },
                    today.get(Calendar.YEAR),
                    today.get(Calendar.MONTH),
                    today.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
    }

    private fun modifyToList() {
        when (fromType) {
            DataUtil.FROM_TYPE.THIS_YEAR -> {
                val copyItem = DataUtil.THIS_YEAR_LIST.get(idx)
                DataUtil.THIS_YEAR_LIST.set(
                    idx,
                    BucketItem(
                        id = copyItem.itemId,
                        text = binding.bucketText.text.toString(),
                        done = copyItem.itemDone,
                        lifetype = copyItem.itemType
                    )
                )
            }
            DataUtil.FROM_TYPE.LIFE -> {
                val copyItem = DataUtil.LIFE_LIST[item.itemType!!].get(idx)
                DataUtil.LIFE_LIST[item.itemType!!].set(
                    idx,
                    BucketItem(
                        id = copyItem.itemId,
                        text = binding.bucketText.text.toString(),
                        done = copyItem.itemDone,
                        lifetype = copyItem.itemType
                    )
                )
            }
            else -> {}
        }
    }


    private fun modifyToDB() {
        // 아이템 텍스트 변경 시 DB 작업
        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                when (fromType) {
                    DataUtil.FROM_TYPE.THIS_YEAR -> {
                        val modifiedItem = DataUtil.THIS_YEAR_LIST.get(idx)
                        ThisYearBucketDB.getInstance(context)!!.thisYearBucketDao().updateText(
                            modifiedItem.itemText,
                            modifiedItem.itemId
                        )
                    }
                    DataUtil.FROM_TYPE.LIFE -> {
                        val modifiedItem = DataUtil.LIFE_LIST[item.itemType!!].get(idx)
                        LifeBucketDB.getInstance(context)!!.lifebucketDao().updateText(
                            modifiedItem.itemText,
                            modifiedItem.itemId
                        )
                    }
                    else -> {}
                }

            }.await()
        }
    }
}