package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.PostViewModel
import ru.netology.nmedia.utils.Utils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likesCount.text = Utils.getWordFromInt(post.likes)
                watchesCount.text = Utils.getWordFromInt(post.watches)
                shareCount.text = Utils.getWordFromInt(post.reposts)
                likeIcon.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24dp else R.drawable.ic_like_24dp)
            }
        }

        binding.likeIcon.setOnClickListener {
            viewModel.like()
        }

        binding.shareIcon.setOnClickListener {
            viewModel.share()
        }
    }
}