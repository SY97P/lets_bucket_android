package com.example.letsbucket.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LifeBucket(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var text: String,
    var done: Boolean
)