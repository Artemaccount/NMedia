package ru.netology.nmedia.db.convert

import ru.netology.nmedia.db.entity.PostEntity
import ru.netology.nmedia.dto.Post

internal fun PostEntity.toModel() = Post(
    id = id,
    author = author,
    published = published,
    content = content,
    likes = likes,
    reposts = reposts,
    watches = watches,
    likedByMe = likedByMe,
    video = video
)

internal fun Post.toPostEntity() = PostEntity(
    id = id,
    author = author,
    published = published,
    content = content,
    likes = likes,
    reposts = reposts,
    watches = watches,
    likedByMe = likedByMe,
    video = video
)
