package com.example.letsbucket.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import com.example.letsbucket.R
import com.example.letsbucket.adaptor.TabAdapter
import com.example.letsbucket.databinding.ActivityMainBinding
import com.example.letsbucket.fragment.ThisYearFragment
import com.example.letsbucket.fragment.LifeTypeFragment
import com.example.letsbucket.util.LogUtil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var initialBackTime: Long = 0
    private var toast: Toast? = null

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
        binding.viewPager.setOnClickListener {
            toast?.cancel()
        }
    }

    override fun onBackPressed() {
        LogUtil.d((System.currentTimeMillis() - initialBackTime).toString())

        if (System.currentTimeMillis() - initialBackTime <= 2000) {
            toast?.cancel()
            finish()
        } else {
            initialBackTime = System.currentTimeMillis()
            toast = Toast.makeText(this, "뒤로가기 버튼을 한 번 더 눌러 앱을 종료합니다.", Toast.LENGTH_LONG)
            toast!!.show()
        }
    }
}