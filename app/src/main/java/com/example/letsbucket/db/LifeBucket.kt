package com.example.letsbucket.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.letsbucket.data.BucketItem

@Entity
data class LifeBucket(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var text: String,
    var done: Boolean,
    var type: Int
) {
    fun converToBucket(): BucketItem = BucketItem(id, text, done, type)
}