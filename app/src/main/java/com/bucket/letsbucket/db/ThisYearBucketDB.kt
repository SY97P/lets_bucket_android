package com.bucket.letsbucket.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Room DataBase
 *
 * DB 생성, 관리 추상 클래스
 *
 * DB 인스턴스 생성을 위해 필요
 */
@Database(entities = [ThisYearBucket::class], version = 5)
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
                    )
                        .addMigrations(MIGRATION_1_2)
                        .addMigrations(MIGRATION_2_3)
                        .addMigrations(MIGRATION_3_4)
                        .addMigrations(MIGRATION_4_5)
                        .addMigrations(MIGRATION_5_6)
                        .addMigrations(MIGRATION_6_7)
                        .build()
                }
            }
            return instance
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ThisYearBucket RENAME COLUMN bucket TO text")
            }
        }
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ThisYearBucket ADD COLUMN date TEXT NOT NULL default 0")
            }
        }
        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ThisYearBucket ADD COLUMN uri TEXT NOT NULL default 0")
            }
        }
        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ThisYearBucket RENAME COLUMN date TO doneDate")
            }
        }
        private val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ThisYearBucket ADD COLUMN targetDate TEXT NOT NULL default 0")
            }
        }
        private val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ThisYearBucket ADD COLUMN detailText TEXT")
            }
        }
    }
}