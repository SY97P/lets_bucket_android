package com.bucket.letsbucket.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues
import android.icu.util.Calendar
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import coil.load
import com.bucket.letsbucket.R
import com.bucket.letsbucket.data.BucketItem
import com.bucket.letsbucket.data.DetailData
import com.bucket.letsbucket.databinding.ActivityDetailBinding
import com.bucket.letsbucket.db.LifeBucketDB
import com.bucket.letsbucket.listener.DismissListener
import com.bucket.letsbucket.dialog.AlertUtilDialog
import com.bucket.letsbucket.dialog.AlertNAnimDialog
import com.bucket.letsbucket.util.DataUtil
import com.bucket.letsbucket.util.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class DetailActivity : AppCompatActivity(), DismissListener {
    private val TAG = "DetailActivity"

    // Binding
    private lateinit var binding: ActivityDetailBinding

    // Gallery Task
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        LogUtil.d(TAG, "URI : " + uri.toString())
        if (uri != null) {
            this.uri = uri.toString()
        }
    }

    // Camera Task -> Store
    private var pictureUri: Uri? = null
    private val cameraStoreLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            this.uri = pictureUri.toString()
        }
    }

    // Camera Task -> Preview (Not Store)
    private val cameraPreviewLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            binding.bucketImage.setImageBitmap(bitmap)
        }
    }

    // Parcelable Data
    private lateinit var data: DetailData

    // Variable which changes View
    private var done: Boolean by Delegates.observable(false) { property, oldValue, newValue ->
        if (newValue) {
            binding.bucketCheck.setImageResource(R.drawable.checked)
        } else {
            binding.bucketCheck.setImageResource(R.drawable.unchecked)
        }
    }
    private var doneDate: String by Delegates.observable("") { property, oldValue, newValue ->
        binding.calendarDoneText.text = newValue
    }
    private var targetDate: String by Delegates.observable("") { property, oldValue, newValue ->
        binding.calendarTargetText.text = newValue
    }
    private var uri: String by Delegates.observable("") { property, oldValue, newValue ->
        if (newValue == "") {
            binding.noImageHintText.visibility = View.VISIBLE
            binding.layoutImage.setBackgroundResource(R.color.grey)
        } else {
            binding.noImageHintText.visibility = View.GONE
            binding.layoutImage.setBackgroundResource(R.color.pastel_orange)
            binding.bucketImage.load(newValue)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)

        data = intent.getParcelableExtra("DATA")!!
        this.done = data.done
        this.doneDate = data.doneDate.toString()
        this.targetDate = data.targetDate.toString()
        this.uri = data.uri.toString()

        checkInvalidAccess()
        setupBinding()

        setContentView(binding.root)
    }

    override fun onDismiss() {
        onBackPressed()
    }

    override fun onDismiss(itemIdx: Int) {
        when (itemIdx) {
            0 -> galleryLauncher.launch("image/*")
            1 -> {
                if (DataUtil.SETTING_DATA.storeImg) {
                    pictureUri = createImageFile()
                    cameraStoreLauncher.launch(pictureUri)
                } else {
                    cameraPreviewLauncher.launch(null)
                }
            }
        }
    }

    private fun checkInvalidAccess() {
        if (data.lifetype == null) {
            LogUtil.d(TAG, "lifeType is null -> invalid access")
            onBackPressed()
        } else if (data.idx < 0 || data.idx >= DataUtil.BUCKET_LIST[data.lifetype!!].size) {
            LogUtil.d(TAG,"index out of range -> invalid access")
            onBackPressed()
        }
    }

    private fun setupBinding() {
        binding.let {
            // 버킷리스트 텍스트
            it.bucketText.setText(data.text)
            // 완료일
            it.calendarDoneText.text = data.doneDate
            // 목표일
            it.calendarTargetText.text = data.targetDate
            // 달성 여부
            if (data.done) {
                it.bucketCheck.setImageResource(R.drawable.checked)
            } else {
                it.bucketCheck.setImageResource(R.drawable.unchecked)
            }
            // 세부 텍스트
            if (data.detailText != null) {
                it.bucketDetailText.setText(data.detailText)
            }

            // 뒤로가기 버튼
            it.buttonBack.setOnClickListener { onBackPressed() }
            
            // 도움말 버튼
//            it.buttonHelp.setOnClickListener {
//                // TODO: 도움말 팝업
//            }

            // 확인 버튼
            it.buttonConfirm.setOnClickListener {
                if (binding.bucketText.text.length in 1 .. 15) {
                    if (this.done && this.doneDate.isBlank()) {
                        var today = GregorianCalendar()
                        this.doneDate = "${today.get(Calendar.YEAR)}/${today.get(Calendar.MONTH)+1}/${today.get(Calendar.DAY_OF_MONTH)}"
                    }
                    modifyToList()
                    modifyToDB()
                    DataUtil.DATA_CHANGED_LISTENER?.dataChanged()
                    if (this.done) {
                        AlertNAnimDialog(this).let {
                            it.build(DataUtil.ANIM_TYPE.FIRE_WORK)
                            it.setDismissListener(this)
                        }
                    } else {
                        onBackPressed()
                    }
                } else {
                    Toast.makeText(this, "버킷리스트를 작성해주세요!", Toast.LENGTH_SHORT).show()
                    AlertUtilDialog(this@DetailActivity, DataUtil.DIALOG_TYPE.BLANK_BUCKET).let {
                        it.build(binding.bucketText.text.toString())
                        it.show()
                    }
                }
            }

            // 아이템 완료 버튼
            it.bucketCheck.setOnClickListener { this.done = !this.done }

            // 달성 캘린더뷰
            it.calendarDoneLayout.setOnClickListener {
                datePickerTask(isTarget = false)
            }

            // 목표 캘린더뷰
            it.calendarTargetLayout.setOnClickListener {
                datePickerTask(isTarget = true)
            }

            // 이미지뷰
            it.bucketImage.setOnClickListener {
                LogUtil.d(TAG, "choose image from local gallery")
                AlertUtilDialog(this@DetailActivity, DataUtil.DIALOG_TYPE.SNAPSHOT).let {
                    it.setDismissListener(this@DetailActivity)
                    it.build(null)
                    it.show()
                }
            }
        }
    }

    private fun datePickerTask(isTarget: Boolean) {
        val today = GregorianCalendar()
        DatePickerDialog(
            this,
            R.style.DatePickerStyle,
            { view, year, month, dayOfMonth ->
                if (isTarget) {
                    this.targetDate = "${year}/${month + 1}/${dayOfMonth}"
                } else {
                    this.doneDate = "${year}/${month + 1}/${dayOfMonth}"
                }
            },
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ).let {
            if (isTarget) {
                it.setIcon(R.drawable.target_calendar)
            } else {
                it.setIcon(R.drawable.done_calendar)
            }
            it.show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun createImageFile(): Uri? {
        val now = SimpleDateFormat("yyMMdd_HHmmss").format(Date())
        val content = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "img_bucket_$now.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }

    private fun modifyToList() {
        DataUtil.BUCKET_LIST[data.lifetype!!].set(
            data.idx,
            BucketItem(
                id = data.id,
                text = binding.bucketText.text.toString(),
                done = this.done,
                lifetype = data.lifetype,
                doneDate = this.doneDate,
                targetDate = this.targetDate,
                uri = this.uri,
                detailText = binding.bucketDetailText.text.toString()
            )
        )
    }

    private fun modifyToDB() {
        // 아이템 텍스트 변경 시 DB 작업
        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                val modifiedItem = DataUtil.BUCKET_LIST[data.lifetype!!].get(data.idx)
                LifeBucketDB.getInstance(this@DetailActivity)!!.lifebucketDao()
                    .updateItem(
                        modifiedItem.itemText,
                        modifiedItem.itemDone,
                        modifiedItem.itemDoneDate,
                        modifiedItem.itemTargetDate,
                        modifiedItem.itemUri,
                        modifiedItem.itemDetailText,
                        modifiedItem.itemId
                    )
            }.await()
        }
    }
}