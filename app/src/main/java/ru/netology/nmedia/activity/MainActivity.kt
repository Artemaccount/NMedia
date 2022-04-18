package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.model.PostViewModel

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

        viewModel.shareEvent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, postContent)
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        val editPostLauncher =
            registerForActivityResult(EditPostActivity.ResultContract) { editPostContent ->
                editPostContent ?: return@registerForActivityResult
                viewModel.editContent(editPostContent)
            }

        viewModel.navigateToEditPostScreen.observe(this) { postContent ->
            editPostLauncher.launch(postContent)
        }

        val newPostLauncher =
            registerForActivityResult(NewPostActivity.ResultContract) { newPostContent ->
                newPostContent ?: return@registerForActivityResult
                viewModel.changeContent(newPostContent)
                viewModel.save()
            }


        viewModel.navigateToNewPostScreen.observe(this) { postContent ->
            newPostLauncher.launch()
        }

        binding.fab.setOnClickListener {
            viewModel.onnAddNewPostButtonClicked()
        }


    }
}
