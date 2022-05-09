package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
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

        var post: Post = requireArguments().getParcelable(POST_KEY)!!
        val listener = viewModel
        val holder = PostViewHolder(binding.postCell, listener)
        holder.bind(post)

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            posts.find { s -> s.id == post.id }?.let { holder.bind(it) }
        }
    }.root


    companion object {
        const val POST_KEY = "postKey"
    }
}