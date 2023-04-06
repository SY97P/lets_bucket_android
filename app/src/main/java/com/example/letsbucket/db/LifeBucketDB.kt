package com.example.letsbucket.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [LifeBucket::class], version=1)
abstract class LifeBucketDB : RoomDatabase() {
    abstract fun lifebucketDao(): LifeBucketDao

    companion object {
        private var instance: LifeBucketDB? = null

        @Synchronized
        fun getInstance(context: Context): LifeBucketDB? {
            if (LifeBucketDB.instance == null) {
                synchronized(LifeBucketDB::class) {
                    LifeBucketDB.instance = Room.databaseBuilder(
                        context.applicationContext,
                        LifeBucketDB::class.java,
                        "life_database"
                    ).build()
                }
            }
            return instance
        }
    }
}