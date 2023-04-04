package com.example.letsbucket.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ThisYearBucket (
//    var id: Int,
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var bucket: String,
    var done: Boolean
)