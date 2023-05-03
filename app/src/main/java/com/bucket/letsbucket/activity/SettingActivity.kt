package com.bucket.letsbucket.activity

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.bucket.letsbucket.BuildConfig
import com.bucket.letsbucket.R
import com.bucket.letsbucket.databinding.ActivitySettingBinding
import com.bucket.letsbucket.db.SettingDB
import com.bucket.letsbucket.db.SettingData
import com.bucket.letsbucket.util.DataUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingBinding.inflate(layoutInflater)

        setupBinding()

        setContentView(binding.root)
    }

    private fun setupBinding() {
        binding.let {
            it.buttonSwitchStoreImg.isChecked = DataUtil.SETTING_DATA.storeImg
            it.buttonSwitchViewHelp.isChecked = DataUtil.SETTING_DATA.viewHelp

            it.buttonBack.setOnClickListener { onBackPressed() }

            it.layoutAppInfo.setOnClickListener {
                var data = arrayOf<String>("🎃 개발자: 박세영", "📋 버전 : ${BuildConfig.VERSION_NAME}")
                AlertDialog.Builder(this@SettingActivity, R.style.AlertDialogStyle)
                    .setIcon(R.drawable.basic)
                    .setTitle("어플리케이션 정보")
                    .setItems(data, DialogInterface.OnClickListener { dialog, which ->
                        //
                    })
                    .show()
            }

            it.layoutMarketReview.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.bucket.letsbucket")
                    )
                )
            }

            it.buttonSwitchStoreImg.setOnClickListener {
                updateSetting()
            }

            it.buttonSwitchViewHelp.setOnClickListener {
                updateSetting()
            }
        }
    }

    private fun updateSetting() {
        DataUtil.SETTING_DATA?.storeImg = binding.buttonSwitchStoreImg.isChecked
        DataUtil.SETTING_DATA?.viewHelp = binding.buttonSwitchViewHelp.isChecked

        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                SettingDB.getInstance(this@SettingActivity)!!.settingDao().update(
                    SettingData(
                        0,
                        binding.buttonSwitchStoreImg.isChecked,
                        binding.buttonSwitchViewHelp.isChecked
                    )
                )
            }
        }
    }
}