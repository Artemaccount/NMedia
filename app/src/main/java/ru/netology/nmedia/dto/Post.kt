package ru.netology.nmedia.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likes: Int = 0,
    val reposts: Int = 0,
    val watches: Int = 0,
    val likedByMe: Boolean = false,
    val video: String
) : Parcelable
