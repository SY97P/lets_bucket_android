package com.example.letsbucket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.letsbucket.adaptor.TabAdapter
import com.example.letsbucket.databinding.ActivityMainBinding
import com.example.letsbucket.fragment.PastFragment
import com.example.letsbucket.fragment.ThisYearFragment
import com.example.letsbucket.fragment.LifeFragment
import com.example.letsbucket.util.DataUtil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("mylog > MainActivity", DataUtil.thisYearBucketList.size.toString())
    }

    override fun onStart() {
        super.onStart()

        val adapter = TabAdapter(supportFragmentManager)
        adapter.addFragment(PastFragment(), getString(R.string.past))
        adapter.addFragment(ThisYearFragment(), getString(R.string.this_year))
        adapter.addFragment(LifeFragment(), getString(R.string.life))

        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        Log.d("mylog > MainActivity", DataUtil.thisYearBucketList.size.toString())
    }
}