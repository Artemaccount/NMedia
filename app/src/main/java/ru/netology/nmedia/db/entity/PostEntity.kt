package ru.netology.nmedia.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
class PostEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "published")
    val published: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "likes")
    val likes: Int = 0,
    @ColumnInfo(name = "reposts")
    val reposts: Int = 0,
    @ColumnInfo(name = "watches")
    val watches: Int = 0,
    @ColumnInfo(name = "likedByMe")
    val likedByMe: Boolean,
    @ColumnInfo(name = "video")
    val video: String
)