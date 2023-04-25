package com.bucket.letsbucket.data

data class LifeTypeItem(
    private val image: Int,
    private val stringRes: Int
) {
    var lifeImage: Int = image
    var lifeString: Int = stringRes
}