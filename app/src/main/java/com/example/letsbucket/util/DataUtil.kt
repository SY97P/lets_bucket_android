package com.example.letsbucket.util

import android.util.Log
import com.example.letsbucket.R
import com.example.letsbucket.data.LifeItem
import com.example.letsbucket.data.ThisYearItem
import kotlin.properties.Delegates

object DataUtil {
    var thisYearBucketList: ArrayList<ThisYearItem> by Delegates.observable(arrayListOf()) {
        property, oldValue, newValue ->
    }

    var lifeList: ArrayList<LifeItem> = arrayListOf(
        LifeItem(R.drawable.baseline_camera_enhance_24, "전체보기"),
        LifeItem(R.drawable.baseline_camera_enhance_24, "여행"),
        LifeItem(R.drawable.baseline_camera_enhance_24, "취미/문화"),
        LifeItem(R.drawable.baseline_camera_enhance_24, "자기계발"),
        LifeItem(R.drawable.baseline_camera_enhance_24, "소비/저축"),
        LifeItem(R.drawable.baseline_camera_enhance_24, "가족/친구"),
        LifeItem(R.drawable.baseline_camera_enhance_24, "기타"),
    )

    var NONE: Int = -1

    var TAG : String = "MYLOG > "

    enum class POPUP_TYPE {
        ADD, MODIFY
    }

    public fun printThisYearBucketList() {
        for (item in thisYearBucketList) {
            Log.d(TAG, "id : " + item.itemId + " text : " + item.itemText + " done : " + item.itemDone)
        }
    }
}