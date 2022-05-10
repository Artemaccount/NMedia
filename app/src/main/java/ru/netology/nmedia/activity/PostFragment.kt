package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.PostFragmentBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.PostViewModel
import ru.netology.nmedia.utils.Utils

class PostFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = PostFragmentBinding.inflate(inflater, container, false).also { binding ->

        var post: Post = requireNotNull(arguments?.getParcelable(POST_KEY))
        val listener = viewModel
        val holder = PostViewHolder(binding.postCell, listener)
        holder.bind(post)

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            posts.find { s -> s.id == post.id }?.let { holder.bind(it) } ?: findNavController().navigateUp()
        }

        viewModel.navigateToEditPostScreen.observe(viewLifecycleOwner) { editing ->
            val result = bundleOf(EditPostFragment.EDITING_POST_KEY to editing)
            findNavController().navigate(R.id.toEditPostFragment, result)
        }

        viewModel.shareEvent.observe(viewLifecycleOwner) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, postContent)
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

    }.root


    companion object {
        const val POST_KEY = "postKey"
    }
}