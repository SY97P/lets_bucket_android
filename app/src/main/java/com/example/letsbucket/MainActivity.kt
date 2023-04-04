package com.example.letsbucket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.letsbucket.adaptor.TabAdapter
import com.example.letsbucket.databinding.ActivityMainBinding
import com.example.letsbucket.fragment.PastFragment
import com.example.letsbucket.fragment.ThisYearFragment
import com.example.letsbucket.fragment.LifeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = TabAdapter(supportFragmentManager)
        adapter.addFragment(PastFragment(), getString(R.string.past))
        adapter.addFragment(ThisYearFragment(), getString(R.string.this_year))
        adapter.addFragment(LifeFragment(), getString(R.string.life))

        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.fab.setOnClickListener(View.OnClickListener {
            Log.d("mylog", "floating button onclick")
        })
    }
}