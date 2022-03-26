package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.Utils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            1,
            "Нетология. Университет интернет-профессий будущего",
            "21 мая в 18:36",
            "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http",
            999_999, 9999, 1_000_000
        )


        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likesCount.text = Utils.getWordFromInt(post.likes)
            watchesCount.text = Utils.getWordFromInt(post.watches)
            shareCount.text = Utils.getWordFromInt(post.reposts)
            likeIcon.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24dp else R.drawable.ic_like_24dp)

            likeIcon.setOnClickListener {
                if (post.likedByMe) post.likes-- else post.likes++
                post.likedByMe = !post.likedByMe
                likesCount.text = Utils.getWordFromInt(post.likes)
                likeIcon.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24dp else R.drawable.ic_like_24dp)
            }
            shareIcon.setOnClickListener {
                post.reposts++
                shareCount.text = Utils.getWordFromInt(post.reposts)

            }
        }
    }
}