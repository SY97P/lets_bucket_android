package com.example.letsbucket.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.letsbucket.data.BucketItem

@Entity
data class ThisYearBucket (
//    var id: Int,
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var text: String,
    var done: Boolean,
    var date: String,
    var uri: String,
) {
    fun convertToList(): BucketItem = BucketItem(id, text, done, null, date, uri)
}