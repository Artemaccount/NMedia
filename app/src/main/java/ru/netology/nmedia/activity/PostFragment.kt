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
import ru.netology.nmedia.databinding.PostFragmentBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.PostViewModel
import ru.netology.nmedia.utils.Utils

class PostFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = PostFragmentBinding.inflate(inflater, container, false).also { binding ->

        var post: Post = requireArguments().getParcelable(POST_KEY)!!

        val popupMenu by lazy {
            PopupMenu(container?.context, binding.postCell.menu).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            viewModel.onRemove(post)
                            findNavController().navigate(R.id.feedFragment)
                            true
                        }
                        R.id.editButton -> {
                            val result = Bundle(1).apply {
                                putParcelable(EditPostFragment.EDITING_POST_KEY, post)
                            }
                            findNavController().navigate(R.id.toEditPostFragment, result)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        binding.postCell.menu.setOnClickListener {
            popupMenu.show()
        }

        with(binding.postCell) {
                    author.text = post.author
                    published.text = post.published
                    content.text = post.content
                    like.text = Utils.getWordFromInt(post.likes)
                    share.text = Utils.getWordFromInt(post.reposts)
                    watches.text = Utils.getWordFromInt(post.watches)
                    like.isChecked = post.likedByMe
        }
    }.root


    companion object {
        const val POST_KEY = "postKey"
    }

}