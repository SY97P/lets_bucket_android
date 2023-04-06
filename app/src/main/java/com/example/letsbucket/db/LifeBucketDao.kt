package com.example.letsbucket.db

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

    @Delete
    fun delete(lifeBucket: LifeBucket)

    @Query("DELETE FROM LifeBucket")
    fun deleteAll()

    @Query("SELECT * FROM LifeBucket")
    fun getAll(): List<LifeBucket>

    @Query("DELETE FROM LifeBucket WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT COUNT(*) FROM LifeBucket")
    fun getCount(): Int
}