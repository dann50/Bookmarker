package com.example.bookmarker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM bookmark")
    fun getBookmarks(): Flow<List<Bookmark>>

    @Query("SELECT * FROM bookmark WHERE title LIKE :key OR page_url LIKE :key")
    fun searchBookmarks(key: String): Flow<List<Bookmark>>

    @Insert
    suspend fun insert(bookmark: Bookmark)

    @Delete
    suspend fun delete(bookmark: Bookmark)

    @Query("DELETE FROM bookmark WHERE id IN (:ids)")
    suspend fun deleteByIds(ids: Array<Int>)
}