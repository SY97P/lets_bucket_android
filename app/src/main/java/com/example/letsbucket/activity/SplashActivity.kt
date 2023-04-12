package com.example.letsbucket.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.letsbucket.R
import com.example.letsbucket.data.BucketItem
import com.example.letsbucket.databinding.ActivitySplashBinding
import com.example.letsbucket.db.LifeBucketDB
import com.example.letsbucket.db.ThisYearBucketDB
import com.example.letsbucket.util.DataUtil
import com.example.letsbucket.util.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class SplashActivity : AppCompatActivity() {

    private var TAG = "SplashActivity"

    private lateinit var thisYearBucketDB: ThisYearBucketDB
    private lateinit var lifeBucketDB: LifeBucketDB

    private lateinit var binding: ActivitySplashBinding

    val imgRes = MutableLiveData<Int>(R.drawable.loading)

    private var dbTaskDone: Boolean by Delegates.observable(false) {
        property, oldValue, newValue ->
        if (newValue) {
            Thread.sleep(1000)
            imgRes.value = R.drawable.start
            binding.button.startAnimation(AnimationUtils.loadAnimation(this, R.anim.cover_appear))
        } else {
            imgRes.value = R.drawable.loading
        }
    }

    private var thisYearDBDone: Boolean by Delegates.observable(false) {
        property, oldValue, newValue ->
        var result = newValue && lifeDBDone
        if (result != dbTaskDone){
            dbTaskDone = !dbTaskDone
        }
    }

    private var lifeDBDone: Boolean by Delegates.observable(false) {
        property, oldValue, newValue ->
        var result = newValue && thisYearDBDone
        if (result != dbTaskDone){
            dbTaskDone = !dbTaskDone
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LogUtil.d("onCreate")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.apply {
            lifecycleOwner = this@SplashActivity
            activity = this@SplashActivity
            button.setOnClickListener {
                if (dbTaskDone) {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else {
                    LogUtil.d("아직 DB 못 읽어옴")
                }
            }
        }

        thisYearBucketDB = ThisYearBucketDB.getInstance(applicationContext)!!
        lifeBucketDB = LifeBucketDB.getInstance(applicationContext)!!
    }

    override fun onStart() {
        super.onStart()
        LogUtil.d("onStart")
        getDBtoList()
    }

    override fun onDestroy() {
        LogUtil.d("onDestroy")
        thisYearDBDone = true
        lifeDBDone = true
        super.onDestroy()
    }

    private fun getDBtoList() {
        getThisYearDB()
        getLifeDB()
    }

    private fun getLifeDB() {
        CoroutineScope(Dispatchers.Main).launch {
            val bucketList = CoroutineScope(Dispatchers.IO).async{
                lifeBucketDB.lifebucketDao().getAll()
            }.await()

            for (itemList in DataUtil.lifelist) {
                itemList.clear()
            }

            for (bucket in bucketList) {
                DataUtil.lifelist[bucket.type].add(
                    bucket.converToBucket()
                )
            }

            for (i in 0 until DataUtil.lifelist.size) {
                if (DataUtil.lifelist.get(i).size <= 0) {
                    DataUtil.lifelist[i].add(
                        BucketItem(System.currentTimeMillis(), "꼭 이루고 싶은 걸 적어보세요", true, i)
                    )
                }
            }

            lifeDBDone = true
        }
    }

    private fun getThisYearDB() {
        CoroutineScope(Dispatchers.Main).launch {
            val bucketList = CoroutineScope(Dispatchers.IO).async {
                thisYearBucketDB.thisYearBucketDao().getAll()
            }.await()

            DataUtil.thisYearBucketList.clear()

            for (bucket in bucketList) {
                DataUtil.thisYearBucketList.add(
                    bucket.convertToList()
                )
            }

            if (DataUtil.thisYearBucketList.size <= 0) {
                DataUtil.thisYearBucketList.add(
                    BucketItem(System.currentTimeMillis(), "올해 목표를 세워보세요!", true, null)
                )
            }

            thisYearDBDone = true
        }
    }
}