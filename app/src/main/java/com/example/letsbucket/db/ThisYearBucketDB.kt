package com.example.letsbucket.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Room DataBase
 *
 * DB 생성, 관리 추상 클래스
 *
 * DB 인스턴스 생성을 위해 필요
 */
@Database(entities = [ThisYearBucket::class], version = 1)
abstract class ThisYearBucketDB: RoomDatabase() {
    abstract fun thisYearBucketDao() : ThisYearBucketDao

    companion object {
        private var instance: ThisYearBucketDB? = null

        @Synchronized
        fun getInstance(context: Context): ThisYearBucketDB? {
            if (instance == null) {
                synchronized(ThisYearBucketDB::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ThisYearBucketDB::class.java,
                        "thisyear_database"
                    ).build()
                }
            }
            return instance
        }
    }
}