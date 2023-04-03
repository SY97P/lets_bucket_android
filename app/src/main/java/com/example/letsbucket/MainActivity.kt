package com.example.letsbucket

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.letsbucket.databinding.ActivityMainBinding
import com.example.letsbucket.tab.TabFragment1
import com.example.letsbucket.tab.TabFragment2
import com.example.letsbucket.tab.TabFragment3
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = TabAdapter(supportFragmentManager)
        adapter.addFragment(TabFragment1(), "탭1")
        adapter.addFragment(TabFragment2(), "탭2")
        adapter.addFragment(TabFragment3(), "탭3")

        binding.viewPager
    }
}