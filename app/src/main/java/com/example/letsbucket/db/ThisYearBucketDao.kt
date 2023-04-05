package com.example.letsbucket.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
/**
 * Data Access Object
 *
 * 데이터 접근 메소드 인터페이스
 */
interface ThisYearBucketDao {
    @Insert
    fun insert(thisYearBucket: ThisYearBucket)

    @Insert
    fun update(thisYearBucket: ThisYearBucket)

    @Delete
    fun delete(thisYearBucket: ThisYearBucket)

    @Query("DELETE FROM ThisYearBucket")
    fun deleteAll()

    @Query("SELECT * FROM ThisYearBucket")
    fun getAll(): List<ThisYearBucket>

    @Query("DELETE FROM ThisYearBucket WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT COUNT(*) FROM ThisYearBucket")
    fun getCount(): Int
}