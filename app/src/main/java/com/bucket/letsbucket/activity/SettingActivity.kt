package com.bucket.letsbucket.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bucket.letsbucket.databinding.ActivitySettingBinding
import com.bucket.letsbucket.db.SettingDB
import com.bucket.letsbucket.db.SettingData
import com.bucket.letsbucket.dialog.AlertUtilDialog
import com.bucket.letsbucket.listener.DismissListener
import com.bucket.letsbucket.util.DataUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SettingActivity : AppCompatActivity(), DismissListener {

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
                AlertUtilDialog(this@SettingActivity, DataUtil.DIALOG_TYPE.APP_INFO).let {
                    it.build(null)
                    it.setDismissListener(this)
                    it.show()
                }
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
        DataUtil.SETTING_DATA.storeImg = binding.buttonSwitchStoreImg.isChecked
        DataUtil.SETTING_DATA.viewHelp = binding.buttonSwitchViewHelp.isChecked

        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                SettingDB.getInstance(this@SettingActivity)!!.settingDao().update(
                    SettingData(
                        0,
                        binding.buttonSwitchStoreImg.isChecked,
                        binding.buttonSwitchViewHelp.isChecked
                    )
                )
            }.await()
        }
    }

    override fun onDismiss() {
        //
    }

    override fun onDismiss(itemIdx: Int) {
        //
    }
}