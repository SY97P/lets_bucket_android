//package com.example.letsbucket.fragment
//
//import android.app.DatePickerDialog
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.icu.util.Calendar
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.example.letsbucket.R
//import com.example.letsbucket.data.BucketItem
//import com.example.letsbucket.databinding.DialogModifyPopupBinding
//import com.example.letsbucket.db.LifeBucketDB
//import com.example.letsbucket.db.ThisYearBucketDB
//import com.example.letsbucket.util.DataUtil
//import com.example.letsbucket.util.LogUtil
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.async
//import kotlinx.coroutines.launch
//import java.util.GregorianCalendar
//import kotlin.properties.Delegates
//
//class ModifyPopupActivity (
//    from: DataUtil.FROM_TYPE,
//    item: BucketItem,
//    idx: Int
//): AppCompatActivity() {
//
//    private lateinit var binding:
//
//    private var fromType: DataUtil.FROM_TYPE
//    private var item: BucketItem
//    private var idx: Int
//    private var done: Boolean by Delegates.observable(item.itemDone) {
//        property, oldValue, newValue ->
//        if (newValue) {
//            binding.bucketCheck.setImageResource(R.drawable.checked)
//        } else {
//            binding.bucketCheck.setImageResource(R.drawable.unchecked)
//        }
//    }
//    private var date: String by Delegates.observable(item.itemDate) {
//        property, oldValue, newValue ->
//        binding.calendarText.text = newValue
//    }
//
//    init {
//        this.fromType = from
//        this.item = item
//        this.idx = idx
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = DialogModifyPopupBinding.inflate(layoutInflater)
//        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        setContentView(binding.root)
//
//        if (fromType == DataUtil.FROM_TYPE.LIFE) {
//            if (item.itemType == null) {
//                LogUtil.d(" TYPE is null")
//                onBackPressed()
//            } else if (idx < 0 || idx >= DataUtil.LIFE_LIST[item.itemType!!].size){
//                LogUtil.d("index out of range")
//                onBackPressed()
//            }
//        } else if (fromType == DataUtil.FROM_TYPE.THIS_YEAR) {
//            if (idx < 0 || idx >= DataUtil.THIS_YEAR_LIST.size) {
//                LogUtil.d("index out of range")
//                onBackPressed()
//            }
//        }
//
//        LogUtil.d(item.itemDate + " " + date + " " + binding.calendarText.text.toString())
//
//        setupBinding()
//    }
//
//    private fun setupBinding() {
//        binding.let {
//            it.bucketText.setText(item.itemText)
//            it.calendarText.setText(item.itemDate)
//            if (item.itemDone) {
//                it.bucketCheck.setImageResource(R.drawable.checked)
//            } else {
//                it.bucketCheck.setImageResource(R.drawable.unchecked)
//            }
//
//            // 뒤로가기 버튼
//            it.buttonBack.setOnClickListener { dismiss() }
//
//            // 확인 버튼
//            it.buttonConfirm.setOnClickListener {
//                if (binding.bucketText.text.length > 0) {
//                    modifyToList()
//                    modifyToDB()
//                    item.printBucketItem()
//                    dismiss()
//                } else {
//                    Toast.makeText(context, "버킷리스트를 작성해주세요!", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            // 아이템 완료 버튼
//            it.bucketCheck.setOnClickListener { done = !done }
//
//            // 캘린더뷰
//            it.calendarLayout.setOnClickListener {
//                val today = GregorianCalendar()
//                DatePickerDialog(
//                    context,
//                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
//                        this.date = "${year}/${month+1}/${dayOfMonth}"
//                    },
//                    today.get(Calendar.YEAR),
//                    today.get(Calendar.MONTH),
//                    today.get(Calendar.DAY_OF_MONTH)
//                ).show()
//            }
//
//            // 이미지뷰
//            it.bucketImage.setOnClickListener {
//                LogUtil.d("이미지뷰 클릭")
////                DataUtil.onRunGalleryListener?.runGallery()
////                val intent = Intent(Intent.ACTION_GET_CONTENT).setType("image/*")
////                val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
////                    uri ->
////                    binding.bucketImage.load(uri)
////                }
////                launcher.launch("image/*")
//            }
//        }
//    }
//
//    private fun modifyToList() {
//        when (fromType) {
//            DataUtil.FROM_TYPE.THIS_YEAR -> {
//                DataUtil.THIS_YEAR_LIST.set(
//                    idx,
//                    BucketItem(
//                        id = item.itemId,
//                        text = binding.bucketText.text.toString(),
//                        done = done,
//                        lifetype = item.itemType,
//                        date = date
//                    )
//                )
//            }
//            DataUtil.FROM_TYPE.LIFE -> {
//                DataUtil.LIFE_LIST[item.itemType!!].set(
//                    idx,
//                    BucketItem(
//                        id = item.itemId,
//                        text = binding.bucketText.text.toString(),
//                        done = done,
//                        lifetype = item.itemType,
//                        date = date
//                    )
//                )
//            }
//            else -> {}
//        }
//    }
//
//    private fun modifyToDB() {
//        // 아이템 텍스트 변경 시 DB 작업
//        CoroutineScope(Dispatchers.Main).launch {
//            CoroutineScope(Dispatchers.IO).async {
//                when (fromType) {
//                    DataUtil.FROM_TYPE.THIS_YEAR -> {
//                        val modifiedItem = DataUtil.THIS_YEAR_LIST.get(idx)
//                        ThisYearBucketDB.getInstance(context)!!.thisYearBucketDao().updateItem(
//                            modifiedItem.itemText,
//                            modifiedItem.itemDone,
//                            modifiedItem.itemDate,
//                            modifiedItem.itemId
//                        )
//                    }
//                    DataUtil.FROM_TYPE.LIFE -> {
//                        val modifiedItem = DataUtil.LIFE_LIST[item.itemType!!].get(idx)
//                        LifeBucketDB.getInstance(context)!!.lifebucketDao().updateItem(
//                            modifiedItem.itemText,
//                            modifiedItem.itemDone,
//                            modifiedItem.itemDate,
//                            modifiedItem.itemId
//                        )
//                    }
//                    else -> {}
//                }
//
//            }.await()
//        }
//    }
//}