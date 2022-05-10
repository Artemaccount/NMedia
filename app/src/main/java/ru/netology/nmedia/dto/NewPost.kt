package ru.netology.nmedia.dto

data class NewPost(
    val userId: Long,
    val userName: String,
    val postId: Long,
    val postAuthor: String,
    val text: String,
)
