package com.bucket.letsbucket.data

import com.bucket.letsbucket.db.LifeBucket
import com.bucket.letsbucket.db.ThisYearBucket

data class BucketItem(
    private val id: Long,
    private val text: String,
    private val done: Boolean,
    private val lifetype: Int?,
    private val doneDate: String,
    private val targetDate: String,
    private val uri: String,
) {
    var itemId: Long = id
    var itemText: String = text
    var itemDone: Boolean = done
    var itemType: Int? = lifetype
    var itemDoneDate: String = doneDate
    var itemTargetDate: String = targetDate
    var itemUri: String = uri

    fun convertToLifeEntity(): LifeBucket =
        LifeBucket(this.itemId, this.itemText, this.itemDone, this.itemType!!, this.itemDoneDate, this.itemTargetDate, this.itemUri)

    fun convertToThisYearEntity(): ThisYearBucket =
        ThisYearBucket(this.itemId, this.itemText, this.itemDone, this.itemDoneDate, this.itemTargetDate, this.itemUri)

//    fun printBucketItem() {
//        LogUtil.d(
//            "itemId: ${itemId}\nitemText: ${itemText}\nitemDone: ${itemDone}\nitemType: ${itemType}\nitemDate: ${itemDate}"
//        )
//    }
}