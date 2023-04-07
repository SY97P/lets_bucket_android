package com.example.letsbucket.util

import com.example.letsbucket.R
import com.example.letsbucket.data.LifeTypeItem
import com.example.letsbucket.data.BucketItem
import kotlin.properties.Delegates

object DataUtil {
    var thisYearBucketList: ArrayList<BucketItem> by Delegates.observable(arrayListOf()) { property, oldValue, newValue ->
    }

    var lifelist: Array<ArrayList<BucketItem>> = arrayOf(
        arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf(),
    )

    var lifeTypeList: ArrayList<LifeTypeItem> = arrayListOf(
        LifeTypeItem(R.drawable.health, "건강"),
        LifeTypeItem(R.drawable.trip, "여행"),
        LifeTypeItem(R.drawable.hobby, "취미/문화"),
        LifeTypeItem(R.drawable.develope, "자기계발"),
        LifeTypeItem(R.drawable.money, "소비/저축"),
        LifeTypeItem(R.drawable.relation, "가족/친구"),
        LifeTypeItem(R.drawable.etc, "기타"),
    )

    enum class MODE_TYPE {
        ADD, MODIFY
    }

    enum class FROM_TYPE {
        LIFE, THIS_YEAR
    }
}