package ru.netology.nmedia.model

import android.app.Activity
import android.content.Intent
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.activity.EditPostActivity
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemory
import ru.netology.nmedia.utils.SingleLiveEvent

class PostViewModel : ViewModel(), PostInteractionListener {

    private val repository: PostRepository = PostRepositoryInMemory()
    val data by repository::data

    private var editedPost = Post(
        id = 0L,
        author = "",
        content = "",
        published = "",
        likedByMe = false
    )

    private val empty = Post(
        id = 0L,
        author = "",
        content = "",
        published = "",
        likedByMe = false
    )

    private val edited = MutableLiveData(empty)

    val shareEvent = SingleLiveEvent<String>()
    val navigateToNewPostScreen = SingleLiveEvent<String>()
    val navigateToEditPostScreen = SingleLiveEvent<String>()


    fun save() {
        val edited = checkNotNull(edited.value) {
            "Edited post should not be null"
        }
        repository.save(edited)
        this.edited.value = empty
    }

    fun editContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        editedPost = editedPost.copy(content = content)
        repository.save(editedPost)
        this.editedPost = empty
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = content)
    }

    fun onnAddNewPostButtonClicked() {
        navigateToNewPostScreen.call()
    }


    //region PostInteractionListener implementation


    override fun onLike(post: Post) {
        repository.likeById(post.id)
    }

    override fun onShare(post: Post) {
        repository.shareById(post.id)
        shareEvent.value = post.content
    }

    override fun onRemove(post: Post) {
        repository.removeById(post.id)
    }

    override fun onEdit(post: Post) {
        navigateToEditPostScreen.value = post.content
        editedPost = post
    }

    //endregion PostInteractionListener

}