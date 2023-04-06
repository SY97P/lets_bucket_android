package com.example.letsbucket.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsbucket.R
import com.example.letsbucket.adaptor.BucketAdapter
import com.example.letsbucket.databinding.ActivityLifeBinding
import com.example.letsbucket.util.DataUtil

class LifeActivity : AppCompatActivity() {

    private var TAG : String = DataUtil.TAG + "LifeActivity"

    private lateinit var binding: ActivityLifeBinding
    private lateinit var lifeAdapter: BucketAdapter
    private var lifeType: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifeType = intent.getIntExtra("LIFE_TYPE", -1)

        binding = ActivityLifeBinding.inflate(layoutInflater)

        lifeAdapter = BucketAdapter(this, DataUtil.FROM_TYPE.LIFE, lifeType, DataUtil.lifelist[lifeType!!])
        binding.lifeBucketList.adapter = lifeAdapter
        binding.lifeBucketList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        setContentView(binding.root)
    }
}