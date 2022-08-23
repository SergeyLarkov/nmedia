package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            binding.render(post)
        }

        binding.likeButton.setOnClickListener {
            viewModel.like()
        }

        binding.shareButton.setOnClickListener {
            viewModel.share()
        }

    }

    private fun ActivityMainBinding.render(post: Post) {
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