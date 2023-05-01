package com.bucket.letsbucket.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bucket.letsbucket.data.BucketItem

@Entity
data class LifeBucket(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var text: String,
    var done: Boolean,
    var type: Int,
    var doneDate: String,
    var targetDate: String,
    var uri: String,
    var detailText: String?
) {
    fun converToBucket(): BucketItem =
        BucketItem(id, text, done, type, doneDate, targetDate, uri, detailText)
}