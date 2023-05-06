package com.bucket.letsbucket.data

import com.bucket.letsbucket.db.LifeBucket

data class BucketItem(
    private val id: Long,
    private val text: String,
    private val done: Boolean,
    private val lifetype: Int?,
    private val doneDate: String,
    private val targetDate: String,
    private val uri: String,
    private val detailText: String?,
) {
    var itemId: Long = id
    var itemText: String = text
    var itemDone: Boolean = done
    var itemType: Int? = lifetype
    var itemDoneDate: String = doneDate
    var itemTargetDate: String = targetDate
    var itemUri: String = uri
    var itemDetailText: String? = detailText

    fun convertToLifeEntity(): LifeBucket =
        LifeBucket(
            this.itemId,
            this.itemText,
            this.itemDone,
            this.itemType!!,
            this.itemDoneDate,
            this.itemTargetDate,
            this.itemUri,
            this.itemDetailText
        )
}