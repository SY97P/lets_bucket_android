package com.bucket.letsbucket.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bucket.letsbucket.dialog.AddPopupDialog
import com.bucket.letsbucket.R
import com.bucket.letsbucket.adaptor.BucketAdapter
import com.bucket.letsbucket.data.BucketItem
import com.bucket.letsbucket.databinding.ActivityLifeBinding
import com.bucket.letsbucket.listener.DismissListener
import com.bucket.letsbucket.util.DataUtil
import com.bucket.letsbucket.util.LogUtil

class LifeActivity : AppCompatActivity() {

    private var TAG: String = "LifeActivity"

    private lateinit var binding: ActivityLifeBinding
    private lateinit var lifeAdapter: BucketAdapter

    private var lifeType: Int? = null
    var subjectImgRes: MutableLiveData<Int> = MutableLiveData()
    var subjectString: MutableLiveData<String> = MutableLiveData()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifeType = intent.getIntExtra("LIFE_TYPE", -1)

        if (DataUtil.BUCKET_LIST[lifeType!!].size == 0) {
            DataUtil.BUCKET_LIST[lifeType!!].add(
                BucketItem(
                    System.currentTimeMillis(),
                    "버튼을 눌러 꼭 이루고 싶은 버킷리스트를 만들어보세요",
                    true,
                    lifeType,
                    "",
                    "",
                    "",
                    null
                )
            )
        }

        binding = DataBindingUtil.setContentView<ActivityLifeBinding?>(this, R.layout.activity_life)
            .apply {
                lifecycleOwner = this@LifeActivity
                activity = this@LifeActivity

                subjectImgRes.value = DataUtil.LIFE_TYPE_LIST.get(lifeType!!).lifeImage
                subjectString.value = getString(DataUtil.LIFE_TYPE_LIST.get(lifeType!!).lifeString)

                lifeAdapter = BucketAdapter(
                    this@LifeActivity,
                    DataUtil.BUCKET_LIST[lifeType!!]
                )
                lifeBucketList.adapter = lifeAdapter
                lifeBucketList.layoutManager =
                    LinearLayoutManager(this@LifeActivity, LinearLayoutManager.VERTICAL, false)

                fab.setOnClickListener(View.OnClickListener {
                    if (DataUtil.BUCKET_LIST[lifeType!!].get(0).itemText.contains("꼭 이루고")) {
                        DataUtil.BUCKET_LIST[lifeType!!].removeAt(0)
                    }
                    AddPopupDialog(
                        this@LifeActivity,
                        lifeType
                    ).let {
                        it.setOnDismissListener {
                            lifeAdapter.notifyDataSetChanged()
                        }
                        it.show()
                    }
                })

//                buttonHelp.setOnClickListener {
//                    AlertAndAnimationUtil(this@LifeActivity, this@LifeActivity)
//                        .build("버킷리스트 클릭!!", "버킷리스트 ")
//                }
            }
    }

    override fun onResume() {
        super.onResume()
        for (item in DataUtil.BUCKET_LIST[lifeType!!]) {
            LogUtil.d(TAG, item.itemId.toString() + " " + item.itemText + " " + item.itemDone)
        }
    }
}