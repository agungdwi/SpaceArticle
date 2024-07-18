package com.compose.spacearticle.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_search")
data class ArticleEntity (
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "url")
    var url: String,

    @ColumnInfo(name = "imageUrl")
    var imageUrl: String,

    @ColumnInfo(name = "newsSite")
    var newsSite: String,

    @ColumnInfo(name = "simmary")
    var summary: String,

    @ColumnInfo(name = "publishedAt")
    var publishedAt: String,

    )