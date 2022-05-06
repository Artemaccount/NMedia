package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
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

        viewModel.navigateToVideoScreen.observe(this) { videoContent ->
            if (!videoContent.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoContent))
                startActivity(intent)
            }
        }


        val editPostLauncher =
            registerForActivityResult(EditPostActivity.ResultContract) { editPostContent ->
                editPostContent ?: return@registerForActivityResult
                viewModel.changeContent(editPostContent)
            }

        viewModel.navigateToEditPostScreen.observe(this)
        { postContent ->
            editPostLauncher.launch(postContent)
        }

        val newPostLauncher =
            registerForActivityResult(NewPostActivity.ResultContract) { newPostContent ->
                newPostContent ?: return@registerForActivityResult
                newPostContent.first?.let { viewModel.changeContent(newPostContent) }
            }


        viewModel.navigateToNewPostScreen.observe(this)
        {
            newPostLauncher.launch()
        }


        binding.fab.setOnClickListener { viewModel.onnAddNewPostButtonClicked() }

    }
}
