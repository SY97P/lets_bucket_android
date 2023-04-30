package com.bucket.letsbucket.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bucket.letsbucket.data.BucketItem

@Entity
data class ThisYearBucket (
//    var id: Int,
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var text: String,
    var done: Boolean,
    var doneDate: String,
    var targetDate: String,
    var uri: String,
) {
    fun convertToList(): BucketItem = BucketItem(id, text, done, null, doneDate, targetDate, uri)
}