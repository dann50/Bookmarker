package com.example.bookmarker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DB_NAME = "app_database"

@Database(entities = [Bookmark::class], version = 2, exportSchema = false)
abstract class BookmarkDatabase : RoomDatabase() {

    abstract fun bookmarkDao() : BookmarkDao

    companion object {
        @Volatile
        private var Instance: BookmarkDatabase? = null

        fun getDatabase(context: Context) : BookmarkDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BookmarkDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}