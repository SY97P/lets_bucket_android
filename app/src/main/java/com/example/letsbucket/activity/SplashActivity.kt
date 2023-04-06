package com.example.letsbucket.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.letsbucket.R
import com.example.letsbucket.data.BucketItem
import com.example.letsbucket.db.ThisYearBucket
import com.example.letsbucket.db.ThisYearBucketDB
import com.example.letsbucket.util.DataUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class SplashActivity : AppCompatActivity() {

    private lateinit var thisYearBucketDB: ThisYearBucketDB
    private var thisYearDB_done: Boolean by Delegates.observable(false) {
        property, oldValue, newValue ->
        if (newValue) {
            findViewById<ImageView>(R.id.button).setImageResource(R.drawable.start)
        } else {
            findViewById<ImageView>(R.id.button).setImageResource(R.drawable.loading)
        }
    }
    private var TAG = DataUtil.TAG + "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Log.d("mylog > SplashActivity", "db size : " + DataUtil.thisYearBucketList.size.toString())

        thisYearBucketDB = ThisYearBucketDB.getInstance(applicationContext)!!

        findViewById<ImageView>(R.id.button).setOnClickListener(View.OnClickListener {
            if (thisYearDB_done) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            } else {
                Log.d("mylog", "아직 DB 못 읽어옴")
            }
        })
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
        getThisYearBucket()
    }

//    override fun onResume() {
//        super.onResume()
//        Log.d(TAG, "onResume")
//        if (!thisYearDB_done) {
//            getThisYearBucket()
//        }
//    }

    override fun onDestroy() {

        Log.d(TAG, "onDestroy")

        CoroutineScope(Dispatchers.IO).launch {
            CoroutineScope(Dispatchers.IO).async {
                thisYearBucketDB.thisYearBucketDao().deleteAll()
                Log.d(TAG, thisYearBucketDB.thisYearBucketDao().getCount().toString())
            }.await()
        }

        CoroutineScope(Dispatchers.IO).launch {
            CoroutineScope(Dispatchers.IO).async {
                for (item in DataUtil.thisYearBucketList) {
                    thisYearBucketDB.thisYearBucketDao().insert(
                        ThisYearBucket(
                            id = item.itemId,
                            bucket = item.itemText,
                            done = item.itemDone
                        )
                    )
                }
                Log.d(TAG, thisYearBucketDB.thisYearBucketDao().getCount().toString())
            }.await()
        }

        Thread.sleep(1000)

        thisYearDB_done = true

        Log.d(TAG, "onDestroy -> DB task done")

        super.onDestroy()
    }

    private fun getThisYearBucket() {
        if (DataUtil.thisYearBucketList.size <= 0) {
            CoroutineScope(Dispatchers.Main).launch {
                val bucketList = CoroutineScope(Dispatchers.IO).async {
                    thisYearBucketDB.thisYearBucketDao().getAll()
                }.await()

                Log.d(
                    TAG,
                    "db size : " + DataUtil.thisYearBucketList.size.toString()
                )

                for (bucket in bucketList) {
                    DataUtil.thisYearBucketList.add(
                        BucketItem(
                            bucket.id,
                            bucket.bucket,
                            bucket.done
                        )
                    )
                }

                thisYearDB_done = true
                Log.d(
                    "mylog > SplashActivity",
                    "list size : " + DataUtil.thisYearBucketList.size.toString()
                )
            }
        }
    }
}