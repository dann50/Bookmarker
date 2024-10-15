package com.example.bookmarker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class Bookmark (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "page_url")
    val pageUrl: String,

    @ColumnInfo(name = "title")
    val pageTitle: String,

    @ColumnInfo(name = "icon_url")
    val iconUrl: String?,

    @ColumnInfo(name = "date")
    val date: String
)