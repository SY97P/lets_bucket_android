package com.bucket.letsbucket.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SettingDao {
    @Insert
    fun insert(settingData: SettingData)

    @Update
    fun update(settingData: SettingData)

    @Delete
    fun delete(settingData: SettingData)

    @Query("SELECT * FROM SettingData WHERE id = :id")
    fun getSetting(id: Int): SettingData

    @Query("SELECT COUNT(*) FROM SettingData")
    fun getCount(): Int
}