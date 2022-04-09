package ru.netology.nmedia

import android.os.Bundle
import android.view.View
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

        viewModel.editPost.observe(this) { post: Post? ->
            val content = post?.content ?: ""
            with(binding) {
                editTextField.setText(content)
                if (viewModel.editPost.value == null) editingGroup.visibility = View.GONE
                else editingGroup.visibility = View.VISIBLE
                editingView.setText(post?.author)
            }
        }

        binding.cancelEditingButton.setOnClickListener {
            with(binding) {
                editTextField.setText("")
                editTextField.clearFocus()
                editTextField.hideKeyBoard()
                editingGroup.visibility = View.GONE
            }
        }


        binding.saveButton.setOnClickListener {
            with(binding) {
                val content = editTextField.text.toString()
                viewModel.onSaveButtonClicked(content)
                editTextField.setText("")
                editTextField.clearFocus()
                editTextField.hideKeyBoard()
            }

        }
    }
}