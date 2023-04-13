package com.example.letsbucket.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
/**
 * Data Access Object
 *
 * 데이터 접근 메소드 인터페이스
 */
interface ThisYearBucketDao {
    @Insert
    fun insert(thisYearBucket: ThisYearBucket)

    @Update
    fun update(thisYearBucket: ThisYearBucket)

    @Query("UPDATE ThisYearBucket SET text = :text WHERE id = :id")
    fun updateText(text: String, id: Long)

    @Query("UPDATE ThisYearBucket SET done = :done WHERE id = :id")
    fun updateDone(done: Boolean, id: Long)

    @Query("UPDATE ThisYearBucket SET text = :text, done = :done, date = :date WHERE id = :id")
    fun updateItem(text: String, done: Boolean, date: String, id: Long)

    @Delete
    fun delete(thisYearBucket: ThisYearBucket)

    @Query("DELETE FROM ThisYearBucket")
    fun deleteAll()

    @Query("DELETE FROM ThisYearBucket WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT * FROM ThisYearBucket")
    fun getAll(): List<ThisYearBucket>


    @Query("SELECT COUNT(*) FROM ThisYearBucket")
    fun getCount(): Int
}