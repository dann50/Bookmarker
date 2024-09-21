package com.example.bookmarker.data

import kotlinx.coroutines.flow.Flow

class BookmarkRepository(private val bookmarkDatabase: BookmarkDatabase) {

    fun getBookmarks(): Flow<List<Bookmark>> = bookmarkDatabase.bookmarkDao().getBookmarks()

    suspend fun insert(bookmark: Bookmark) = bookmarkDatabase.bookmarkDao().insert(bookmark)

    suspend fun delete(bookmark: Bookmark) = bookmarkDatabase.bookmarkDao().delete(bookmark)

    suspend fun deleteByIds(ids: Array<Int>) = bookmarkDatabase.bookmarkDao().deleteByIds(ids)
}