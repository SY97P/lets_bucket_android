package com.example.letsbucket.data

import com.example.letsbucket.db.LifeBucket
import com.example.letsbucket.db.ThisYearBucket

data class BucketItem(private val id: Long, private val text: String, private val done: Boolean, private val lifetype: Int?) {
    var itemId: Long = id
    var itemText: String = text
    var itemDone: Boolean = done
    var itemType: Int? = lifetype

    fun convertToLifeEntity(): LifeBucket = LifeBucket(this.itemId, this.itemText, this.itemDone, this.itemType!!)

    fun convertToThisYearEntity(): ThisYearBucket = ThisYearBucket(this.itemId, this.itemText, this.itemDone)
}