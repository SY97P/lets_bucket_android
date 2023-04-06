package com.example.letsbucket.util

import android.util.Log
import com.example.letsbucket.R
import com.example.letsbucket.data.LifeTypeItem
import com.example.letsbucket.data.BucketItem
import kotlin.properties.Delegates

object DataUtil {
    var thisYearBucketList: ArrayList<BucketItem> by Delegates.observable(arrayListOf()) {
        property, oldValue, newValue ->
    }

    var lifelist: Array<ArrayList<BucketItem>> = arrayOf(
        arrayListOf(
            BucketItem(System.currentTimeMillis(), "전체", false)
        ),
        arrayListOf(
            BucketItem(System.currentTimeMillis(), "여행", false)
        ),
        arrayListOf(
            BucketItem(System.currentTimeMillis(), "취문", false)
        ),
        arrayListOf(
            BucketItem(System.currentTimeMillis(), "계발", false)
        ),
        arrayListOf(
            BucketItem(System.currentTimeMillis(), "저축", false)
        ),
    )

    var lifeTypeList: ArrayList<LifeTypeItem> = arrayListOf(
        LifeTypeItem(R.drawable.health, "전체보기"),
        LifeTypeItem(R.drawable.trip, "여행"),
        LifeTypeItem(R.drawable.hobby, "취미/문화"),
        LifeTypeItem(R.drawable.develope, "자기계발"),
        LifeTypeItem(R.drawable.money, "소비/저축"),
        LifeTypeItem(R.drawable.relation, "가족/친구"),
        LifeTypeItem(R.drawable.etc, "기타"),
    )

    var TAG : String = "MYLOG > "

    enum class MODE_TYPE {
        ADD, MODIFY
    }

    enum class FROM_TYPE {
        LIFE, THIS_YEAR
    }

    fun printThisYearBucketList() {
        for (item in thisYearBucketList) {
            Log.d(TAG, "id : " + item.itemId + "\ntext : " + item.itemText + "\ndone : " + item.itemDone)
        }
    }

    fun printLifeList(lifetype: Int) {
        for (item in lifelist[lifetype]) {
            Log.d(TAG, "id : " + item.itemId + "\ntext : " + item.itemText + "\ndone : " + item.itemDone)
        }
    }
}