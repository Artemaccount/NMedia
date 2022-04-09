package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.PostViewModel
import ru.netology.nmedia.utils.hideKeyBoard

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(viewModel)

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.editPost.observe(this){ post: Post? ->
            val content = post?.content ?: ""
             binding.editTextField.setText(content)
        }

        binding.saveButton.setOnClickListener {
            with(binding.editTextField) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)

                setText("")
                clearFocus()
                hideKeyBoard()
            }

        }
    }
}