package com.bucket.letsbucket.listener

interface DismissListener {
    fun onDismiss()
    fun onDismiss(itemIdx: Int)
}