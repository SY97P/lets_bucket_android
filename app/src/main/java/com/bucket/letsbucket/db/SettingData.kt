package com.bucket.letsbucket.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SettingData(
    @PrimaryKey
    val id: Int = 0,
    var storeImg: Boolean,
    var viewHelp: Boolean,
)
