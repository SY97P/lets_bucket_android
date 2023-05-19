package com.bucket.letsbucket.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bucket.letsbucket.R
import com.bucket.letsbucket.adaptor.TabAdapter
import com.bucket.letsbucket.databinding.ActivityMainBinding
import com.bucket.letsbucket.fragment.ThisYearFragment
import com.bucket.letsbucket.fragment.LifeTypeFragment

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

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
        adapter.addFragment(ThisYearFragment(), getString(R.string.calendar))
//        adapter.addFragment(PastFragment(), getString(R.string.past))

        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.viewPager.setOnClickListener {
            toast?.cancel()
        }

        binding.buttonSetting.setOnClickListener {
            startActivity(Intent(this@MainActivity, SettingActivity::class.java))
        }
    }

    override fun onBackPressed() {
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