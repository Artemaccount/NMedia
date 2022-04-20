package ru.netology.nmedia.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInFileImpl
import ru.netology.nmedia.utils.SingleLiveEvent

class PostViewModel(application: Application) : AndroidViewModel(application), PostInteractionListener {

    private val repository: PostRepository = PostRepositoryInFileImpl(application)
    val data by repository::data

    private var editedPost = Post(
        id = 0L,
        author = "",
        content = "",
        published = "",
        likedByMe = false,
        video = ""
    )

    private val empty = Post(
        id = 0L,
        author = "",
        content = "",
        published = "",
        likedByMe = false,
        video = ""
    )

    private val edited = MutableLiveData(empty)

    val shareEvent = SingleLiveEvent<String>()
    val navigateToNewPostScreen = SingleLiveEvent<String>()
    val navigateToEditPostScreen = SingleLiveEvent<String>()
    val navigateToVideoScreen = SingleLiveEvent<String>()

    fun changeContent(content: Pair<String?, String?>) {
        val textContent = content.first
        val videoContent = content.second
        if (edited.value?.content == textContent) {
            return
        }
        if (textContent != null) {
            editedPost = editedPost.copy(content = textContent)
        }
        if (videoContent != null) {
            editedPost = editedPost.copy(video = videoContent)
        }
        repository.save(editedPost)
        this.editedPost = empty
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

    override fun onVideo(post: Post) {
        navigateToVideoScreen.value = post.video
    }

    //endregion PostInteractionListener

}