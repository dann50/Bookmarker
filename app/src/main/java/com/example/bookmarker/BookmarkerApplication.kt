package com.example.bookmarker

import android.app.Application
import com.example.bookmarker.data.BookmarkDatabase
import com.example.bookmarker.data.BookmarkRepository

class BookmarkerApplication : Application() {

    private val database: BookmarkDatabase by lazy { BookmarkDatabase.getDatabase(this) }
    val bookmarkRepository: BookmarkRepository by lazy { BookmarkRepository(database) }

}