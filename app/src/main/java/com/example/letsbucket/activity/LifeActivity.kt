package com.example.letsbucket.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsbucket.PopupDialog
import com.example.letsbucket.R
import com.example.letsbucket.adaptor.BucketAdapter
import com.example.letsbucket.databinding.ActivityLifeBinding
import com.example.letsbucket.util.DataUtil
import com.example.letsbucket.util.LogUtil

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

        LogUtil.d("LifeActivity", lifeType.toString())

        binding = DataBindingUtil.setContentView<ActivityLifeBinding?>(this, R.layout.activity_life)
            .apply {
                lifecycleOwner = this@LifeActivity
                activity = this@LifeActivity

                subjectImgRes.value = DataUtil.lifeTypeList.get(lifeType!!).lifeImage
                subjectString.value = getString(DataUtil.lifeTypeList.get(lifeType!!).lifeString)


                lifeAdapter = BucketAdapter(
                    this@LifeActivity,
                    DataUtil.FROM_TYPE.LIFE,
                    lifeType,
                    DataUtil.lifelist[lifeType!!]
                )
                lifeBucketList.adapter = lifeAdapter
                lifeBucketList.layoutManager =
                    LinearLayoutManager(this@LifeActivity, LinearLayoutManager.VERTICAL, false)

                fab.setOnClickListener(View.OnClickListener {
                    PopupDialog(
                        this@LifeActivity,
                        DataUtil.MODE_TYPE.ADD,
                        DataUtil.FROM_TYPE.LIFE,
                        lifeType,
                        null
                    ).let {
                        it.setOnDismissListener {
                            lifeAdapter.notifyDataSetChanged()
                        }
                        it.show()
                    }
                })
            }
    }
}