package com.bucket.letsbucket.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LifeBucketDao {
    @Insert
    fun insert(lifeBucket: LifeBucket)

    @Update
    fun update(lifeBucket: LifeBucket)

    @Query("UPDATE LifeBucket SET text = :text WHERE id = :id")
    fun updateText(text: String, id: Long)

    @Query("UPDATE LifeBucket SET done = :done, doneDate = :doneDate WHERE id = :id")
    fun updateDone(done: Boolean, doneDate: String, id: Long)

    @Query("UPDATE LifeBucket SET text = :text, done = :done, doneDate = :doneDate, targetDate = :targetDate, uri = :uri, detailText = :detailText WHERE id = :id")
    fun updateItem(text: String, done: Boolean, doneDate: String, targetDate: String, uri: String, detailText: String?, id: Long)

    @Delete
    fun delete(lifeBucket: LifeBucket)

    @Query("DELETE FROM LifeBucket")
    fun deleteAll()

    @Query("DELETE FROM LifeBucket WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT * FROM LifeBucket")
    fun getAll(): List<LifeBucket>


    @Query("SELECT COUNT(*) FROM LifeBucket")
    fun getCount(): Int
}