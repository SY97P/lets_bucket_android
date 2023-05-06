package com.bucket.letsbucket.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.bucket.letsbucket.R
import com.bucket.letsbucket.data.BucketItem
import com.bucket.letsbucket.databinding.ActivitySplashBinding
import com.bucket.letsbucket.db.LifeBucketDB
import com.bucket.letsbucket.db.SettingDB
import com.bucket.letsbucket.fragment.AnimationDialog
import com.bucket.letsbucket.util.DataUtil
import com.bucket.letsbucket.util.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var TAG = "SplashActivity"

    private lateinit var lifeBucketDB: LifeBucketDB
    private lateinit var settingDB: SettingDB

    private lateinit var binding: ActivitySplashBinding

    private val reqPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            results.forEach {
                if (!it.value) {
                    Toast.makeText(applicationContext, "${it.key} 권한 허용 필요", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            }
            permissionDone = true
        }

    val imgRes = MutableLiveData<Int>(R.drawable.loading)

    private var dbTaskDone: Boolean by Delegates.observable(false) { property, oldValue, newValue ->
        if (newValue) {
            Thread.sleep(1000)
            imgRes.value = R.drawable.start
            binding.button.startAnimation(AnimationUtils.loadAnimation(this, R.anim.cover_appear))
            AnimationDialog(this, DataUtil.ANIM_TYPE.CLICK).show()
        } else {
            imgRes.value = R.drawable.loading
        }
    }

    private var lifeDBDone: Boolean by Delegates.observable(false) { property, oldValue, newValue ->
        val result = newValue && permissionDone && settingDBDone
        if (result != dbTaskDone) {
            dbTaskDone = !dbTaskDone
        }
    }

    private var settingDBDone: Boolean by Delegates.observable(false) { property, oldValue, newValue ->
        val result = newValue && lifeDBDone && permissionDone
        if (result != dbTaskDone) {
            dbTaskDone = !dbTaskDone
        }
    }

    private var permissionDone: Boolean by Delegates.observable(false) { property, oldValue, newValue ->
        val result = newValue && lifeDBDone && settingDBDone
        if (result != dbTaskDone) {
            dbTaskDone = !dbTaskDone
        }
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        reqPermissionLauncher.launch(DataUtil.permissionList)

        binding.apply {
            lifecycleOwner = this@SplashActivity
            activity = this@SplashActivity
            button.setOnClickListener {
                if (dbTaskDone) {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    LogUtil.d(TAG, "Not Read DB yet")
                }
            }
        }

        lifeBucketDB = LifeBucketDB.getInstance(applicationContext)!!
        settingDB = SettingDB.getInstance(applicationContext)!!
    }

    override fun onStart() {
        super.onStart()
        try {
            getDBtoList()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        lifeDBDone = false
        settingDBDone = false
        super.onDestroy()
    }

    private fun getDBtoList() {
        getLifeDB()
        getSetting()
    }

    private fun getLifeDB() {
        CoroutineScope(Dispatchers.Main).launch {
            val bucketList = CoroutineScope(Dispatchers.IO).async {
                lifeBucketDB.lifebucketDao().getAll()
            }.await()

            for (itemList in DataUtil.LIFE_LIST) {
                itemList.clear()
            }

            for (bucket in bucketList) {
                DataUtil.LIFE_LIST[bucket.type].add(
                    bucket.converToBucket()
                )
            }

            for (i in 0 until DataUtil.LIFE_LIST.size) {
                if (DataUtil.LIFE_LIST[i].size <= 0) {
                    DataUtil.LIFE_LIST[i].add(
                        BucketItem(
                            System.currentTimeMillis(),
                            "버튼을 눌러 꼭 이루고 싶은 버킷리스트를 만들어보세요",
                            true,
                            i,
                            "",
                            "",
                            "",
                            null
                        )
                    )
                }
            }

            lifeDBDone = true
            LogUtil.d(TAG, "LifeDBTask done")
        }
    }

    private fun getSetting() {
        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                if (settingDB.settingDao().getCount() == 0) {
                    LogUtil.d(TAG, "SettingDB is Empty")
                    CoroutineScope(Dispatchers.Default).async {
                        settingDB.settingDao().insert(
                            DataUtil.SETTING_DATA
                        )
                    }.await()
                    LogUtil.d(TAG, "SettingDB insert Transaction done")
                } else {
                    LogUtil.d(TAG, "SettingDB has Data")
                    DataUtil.SETTING_DATA = settingDB.settingDao().getSetting(0)
                }
                LogUtil.d(TAG, "SettingDBTask done")
            }.await()

            settingDBDone = true
        }
    }
}