package com.bucket.letsbucket.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SettingData::class], version = 1)
abstract class SettingDB: RoomDatabase() {
    abstract fun settingDao(): SettingDao

    companion object {
        private var instance: SettingDB? = null

        @Synchronized
        fun getInstance(context: Context): SettingDB? {
            if (instance == null) {
                synchronized(SettingDB::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SettingDB::class.java,
                        "setting_database"
                    )
                        .build()
                }
            }
            return instance
        }
    }
}