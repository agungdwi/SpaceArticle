package com.compose.spacearticle.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.compose.spacearticle.data.local.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao

}