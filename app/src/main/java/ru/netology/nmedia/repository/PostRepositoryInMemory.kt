package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemory : PostRepository {

    private var nextId = 3L

    override val data: MutableLiveData<List<Post>>

    init {
        val posts = listOf(
            Post(
                1,
                "Нетология. Университет интернет-профессий будущего",
                "21 мая в 18:36",
                "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http",
                999_999, 9999, 1_000_000
            ),
            Post(
                2, "Нетология. Университет интернет-профессий будущего",
                "21 мая в 18:36",
                "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http",
                999_999, 9999, 1_000_000
            ),
        )
        data = MutableLiveData(posts)
    }


    private val posts
        get() = checkNotNull(data.value) {
            "Live data should be initialized with posts"
        }

    override fun likeById(id: Long) {
        data.value = posts.map {
            if (it.id != id) it else
                if (it.likedByMe) it.copy(likedByMe = !it.likedByMe, likes = it.likes - 1) else
                    it.copy(likedByMe = !it.likedByMe, likes = it.likes + 1)
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        data.value = posts.map {
            if (it.id != id) it else it.copy(reposts = it.reposts + 1)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        data.value = posts.filterNot {
            it.id == id
        }
    }

    private companion object {
        private const val NEW_POST_ID = 0L
    }

    override fun save(post: Post) = if (post.id == NEW_POST_ID) insert(post) else update(post)



    private fun insert(post: Post) {
        val identifiedPost = post.copy(id = nextId++)
        data.value = listOf(identifiedPost) + posts
    }

    private fun update(post: Post) {
        data.value = posts.map{
            if(it.id == post.id) post else it
        }
    }
}