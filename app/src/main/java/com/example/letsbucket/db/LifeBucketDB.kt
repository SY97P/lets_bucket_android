package com.example.letsbucket.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database (entities = [LifeBucket::class], version=4)
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
                    )
                        .addMigrations(MIGRATION_1_2)
                        .addMigrations(MIGRATION_2_3)
                        .addMigrations(MIGRATION_3_4)
                        .build()
                }
            }
            return instance
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE LifeBucket ADD COLUMN type INTEGER NOT NULL default 0")
            }
        }
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE LifeBucket ADD COLUMN date TEXT NOT NULL default 0")
            }
        }
        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE LifeBucket ADD COLUMN uri TEXT NOT NULL default 0")
            }
        }
    }
}