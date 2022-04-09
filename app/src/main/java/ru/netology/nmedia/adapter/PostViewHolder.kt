package ru.netology.nmedia.adapter

import android.system.Os.remove
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostLayoutBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.Utils

class PostViewHolder(
    private val binding: PostLayoutBinding,
    private val listener: PostInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var post: Post

    private val popupMenu by lazy {
        PopupMenu(itemView.context, binding.menu).apply {
            inflate(R.menu.options_post)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.remove -> {
                        listener.onRemove(post)
                        true
                    }
                    R.id.edit -> {
                        listener.onEdit(post)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    init {
        binding.likeIcon.setOnClickListener {
            listener.onLike(post)
        }
        binding.shareIcon.setOnClickListener {
            listener.onShare(post)
        }
        binding.menu.setOnClickListener {
            popupMenu.show()
        }
    }

    fun bind(post: Post) {
        this.post = post
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likesCount.text = Utils.getWordFromInt(post.likes)
            shareCount.text = Utils.getWordFromInt(post.reposts)
            watchesCount.text = Utils.getWordFromInt(post.watches)
            likeIcon.setImageResource(
                if (post.likedByMe) R.drawable.ic_liked_24dp else R.drawable.ic_like_24dp
            )
        }
    }
}