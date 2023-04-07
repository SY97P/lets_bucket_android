package com.example.letsbucket.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.letsbucket.R
import com.example.letsbucket.adaptor.TabAdapter
import com.example.letsbucket.databinding.ActivityMainBinding
import com.example.letsbucket.fragment.ThisYearFragment
import com.example.letsbucket.fragment.LifeTypeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        val adapter = TabAdapter(supportFragmentManager)
        adapter.addFragment(LifeTypeFragment(), getString(R.string.life))
        adapter.addFragment(ThisYearFragment(), getString(R.string.this_year))
//        adapter.addFragment(PastFragment(), getString(R.string.past))

        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
}