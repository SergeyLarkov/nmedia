package ru.netology.nmedia.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.ClickEvents
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.toText

class PostViewHolder(private val binding: CardPostBinding,
                     private val clickEvents:ClickEvents): RecyclerView.ViewHolder(binding.root) {

    private lateinit var post: Post

    private val popupMenu = PopupMenu(itemView.context, binding.optionsButton).apply {
        inflate(R.menu.option_post)
        setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.remove -> {
                    clickEvents.onDeleteClicked(post)
                    true
                }
                R.id.edit -> {
                    clickEvents.onEditClicked(post)
                    true
                }
                else -> false
            }
        }
    }

    init {
        binding.likeButton.setOnClickListener { clickEvents.onLikeClicked(post) }
        binding.shareButton.setOnClickListener { clickEvents.onShareClicked(post) }
        binding.optionsButton.setOnClickListener{ popupMenu.show() }
    }

    fun render(post: Post) {
        this.post = post
        with(binding) {
            authorName.text = post.authorName
            authorAvatarImageView.setImageResource(R.drawable.ic_avatar_24)
            postDate.text = post.postDate.toString()
            postText.text = post.postText
            likeCount.text = toText(post.likesCount)
            shareCount.text = toText(post.shareCount)
            viewCount.text = toText(post.viewCount)
            if (post.liked) {
                likeButton.setImageResource(R.drawable.ic_liked_24)
            } else {
                likeButton.setImageResource(R.drawable.ic_like_24)
            }
        }
    }
}

class PostAdapter(private val clickEvents:ClickEvents) : RecyclerView.Adapter<PostViewHolder>() {

    var list = emptyList<Post>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, clickEvents)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = list[position]
        holder.render(post)
    }
}