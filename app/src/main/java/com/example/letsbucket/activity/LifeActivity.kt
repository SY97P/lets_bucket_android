package com.example.letsbucket.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsbucket.PopupDiaLogUtil
import com.example.letsbucket.R
import com.example.letsbucket.adaptor.BucketAdapter
import com.example.letsbucket.databinding.ActivityLifeBinding
import com.example.letsbucket.util.DataUtil

class LifeActivity : AppCompatActivity() {

    private var TAG: String = "LifeActivity"

    private lateinit var binding: ActivityLifeBinding
    private lateinit var lifeAdapter: BucketAdapter
    private var lifeType: Int? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifeType = intent.getIntExtra("LIFE_TYPE", -1)

        binding = ActivityLifeBinding.inflate(layoutInflater)

        binding.subjectImage.setImageResource(DataUtil.lifeTypeList.get(lifeType!!).lifeImage)
        binding.subjectText.text = getString(DataUtil.lifeTypeList.get(lifeType!!).lifeString)

        lifeAdapter =
            BucketAdapter(this, DataUtil.FROM_TYPE.LIFE, lifeType, DataUtil.lifelist[lifeType!!])
        binding.lifeBucketList.adapter = lifeAdapter
        binding.lifeBucketList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // 버킷리스트 추가
        binding.fab.setOnClickListener(View.OnClickListener {
            PopupDiaLogUtil(
                this,
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

        setContentView(binding.root)
    }
}