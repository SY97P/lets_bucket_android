package com.example.letsbucket.data

data class ThisYearItem(private val id: Long, private val text: String, private val done: Boolean) {
    var itemId: Long = id
    var itemText: String = text
    var itemDone: Boolean = done
}