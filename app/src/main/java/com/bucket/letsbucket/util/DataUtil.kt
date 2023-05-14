package com.bucket.letsbucket.util

import com.bucket.letsbucket.R
import com.bucket.letsbucket.data.LifeTypeItem
import com.bucket.letsbucket.data.BucketItem
import com.bucket.letsbucket.db.SettingData
import com.bucket.letsbucket.listener.DataChangedListener

object DataUtil {

    var LIFE_LIST: Array<ArrayList<BucketItem>> = arrayOf(
        arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf(),
    )

    var LIFE_TYPE_LIST: ArrayList<LifeTypeItem> = arrayListOf(
        LifeTypeItem(R.drawable.health, R.string.health),
        LifeTypeItem(R.drawable.trip, R.string.trip),
        LifeTypeItem(R.drawable.hobby, R.string.hobby),
        LifeTypeItem(R.drawable.develope, R.string.develope),
        LifeTypeItem(R.drawable.money, R.string.money),
        LifeTypeItem(R.drawable.relation, R.string.relation),
        LifeTypeItem(R.drawable.etc, R.string.etc),
    )

    var SETTING_DATA: SettingData = SettingData(0, true, true)

    var DATA_CHANGED_LISTENER: DataChangedListener? = null

    enum class ANIM_TYPE {
        FIRE_WORK, CLICK
    }

    enum class DIALOG_TYPE {
        BUCKET_DONE, APP_INFO, SNAPSHOT, CALENDAR, DEFAULT
    }

    val permissionList = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.CAMERA,
    )
}