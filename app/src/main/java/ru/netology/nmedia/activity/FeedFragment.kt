package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FeedFragmentBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.PostViewModel

class FeedFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //region shareEvent
        viewModel.shareEvent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, postContent)
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }
        //endregion

        viewModel.navigateToPostScreen.observe(this){ post->
            val result = Bundle(1).apply {
                putParcelable(PostFragment.POST_KEY, post)
            }
            findNavController().navigate(R.id.toPostFragment, result)
        }


        viewModel.navigateToEditPostScreen.observe(this) { post ->
            val result = Bundle(1).apply {
                putParcelable(EditPostFragment.EDITING_POST_KEY, post)
            }
            findNavController().navigate(R.id.toEditPostFragment, result)
        }


        viewModel.navigateToNewPostScreen.observe(this)
        {
            findNavController().navigate(R.id.toNewPostFragment)
        }

        parentFragmentManager.setFragmentResultListener(
            NewPostFragment.REQUEST_KEY,
            this
        ) { requestKey, resultBundle ->
            if (requestKey != NewPostFragment.REQUEST_KEY) return@setFragmentResultListener
            val newPostContent = resultBundle.getString(NewPostFragment.NEW_POST_CONTENT_KEY, "")
            val newPostVideo = resultBundle.getString(NewPostFragment.VIDEO_URL_KEY, "")
            val newPost = Post(
                0L, author = "" , content = newPostContent, video = newPostVideo, published = "",
            )
            viewModel.changeContent(newPost)
        }

        parentFragmentManager.setFragmentResultListener(
            EditPostFragment.RESULT_KEY,
            this
        ) { requestKey, resultBundle ->
            if (requestKey != EditPostFragment.RESULT_KEY) return@setFragmentResultListener
            val newPost = resultBundle.getParcelable<Post>(EditPostFragment.EDITING_POST_KEY)!!
            viewModel.changeContent(newPost)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FeedFragmentBinding.inflate(inflater, container, false).also { binding ->

        val adapter = PostsAdapter(viewModel)
        binding.list.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        viewModel.navigateToVideoScreen.observe(viewLifecycleOwner) { videoContent ->
            if (!videoContent.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoContent))
                startActivity(intent)
            }
        }

        binding.fab.setOnClickListener { viewModel.onnAddNewPostButtonClicked() }

    }.root

}
