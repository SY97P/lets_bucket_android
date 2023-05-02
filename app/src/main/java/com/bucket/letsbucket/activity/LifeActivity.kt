package com.bucket.letsbucket.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bucket.letsbucket.fragment.AddPopupDialog
import com.bucket.letsbucket.R
import com.bucket.letsbucket.adaptor.BucketAdapter
import com.bucket.letsbucket.databinding.ActivityLifeBinding
import com.bucket.letsbucket.fragment.AnimationDialog
import com.bucket.letsbucket.util.DataUtil
import com.bucket.letsbucket.util.LogUtil
import com.tomergoldst.tooltips.ToolTip
import com.tomergoldst.tooltips.ToolTipsManager

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

        LogUtil.d(TAG, lifeType.toString())

        binding = DataBindingUtil.setContentView<ActivityLifeBinding?>(this, R.layout.activity_life)
            .apply {
                lifecycleOwner = this@LifeActivity
                activity = this@LifeActivity

                subjectImgRes.value = DataUtil.LIFE_TYPE_LIST.get(lifeType!!).lifeImage
                subjectString.value = getString(DataUtil.LIFE_TYPE_LIST.get(lifeType!!).lifeString)


                lifeAdapter = BucketAdapter(
                    this@LifeActivity,
                    DataUtil.FROM_TYPE.LIFE,
                    DataUtil.LIFE_LIST[lifeType!!]
                )
                lifeBucketList.adapter = lifeAdapter
                lifeBucketList.layoutManager =
                    LinearLayoutManager(this@LifeActivity, LinearLayoutManager.VERTICAL, false)

                fab.setOnClickListener(View.OnClickListener {
                    AddPopupDialog(
                        this@LifeActivity,
                        DataUtil.FROM_TYPE.LIFE,
                        lifeType
                    ).let {
                        it.setOnDismissListener {
                            lifeAdapter.notifyDataSetChanged()
                        }
                        it.show()
                    }
                })

                buttonHelp.setOnClickListener {

                }
            }
    }

    override fun onResume() {
        super.onResume()
        for (item in DataUtil.LIFE_LIST[lifeType!!]) {
            LogUtil.d(TAG, item.itemId.toString() + " " + item.itemText + " " + item.itemDone)
        }
    }
}