package ru.netology.nmedia.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.toText

class PostViewHolder(private val binding: CardPostBinding,
                     private val onLikeClicked: (post: Post) -> Unit,
                     private val onShareClicked: (post: Post) -> Unit): RecyclerView.ViewHolder(binding.root) {

    fun render(post: Post) {
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
            likeButton.setOnClickListener { onLikeClicked(post) }
            shareButton.setOnClickListener { onShareClicked(post) }
        }
    }
}

class PostAdapter(private val onLikeClicked: (post: Post) -> Unit,
                  private val onShareClicked: (post: Post) -> Unit) : RecyclerView.Adapter<PostViewHolder>() {

    var list = emptyList<Post>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeClicked, onShareClicked)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = list[position]
        holder.render(post)
    }
}