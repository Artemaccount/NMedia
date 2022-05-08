package ru.netology.nmedia.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.*
import ru.netology.nmedia.utils.SingleLiveEvent

class PostViewModel(application: Application) : AndroidViewModel(application),
    PostInteractionListener {

    private val repository: PostRepository = PostRepositoryRoomImpl(
        AppDb.getInstance(application).postDao
    )
    val data by repository::data

    val shareEvent = SingleLiveEvent<String>()
    val navigateToNewPostScreen = SingleLiveEvent<String>()
    val navigateToEditPostScreen = SingleLiveEvent<Post>()
    val navigateToVideoScreen = SingleLiveEvent<String>()
    val navigateToPostScreen = SingleLiveEvent<Post>()

    fun changeContent(post: Post) {
        repository.save(post)
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
        navigateToEditPostScreen.value = post
    }

    override fun onVideo(post: Post) {
        navigateToVideoScreen.value = post.video
    }

    override fun onListItem(post: Post) {
        navigateToPostScreen.value = post
    }

    //endregion PostInteractionListener

}