package ru.netology.nmedia

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
// synthetic см. build.gradle (app)
//import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.databinding.ActivityMainBinding
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    data class Post(
        val id: Int,
        val authorName: String,
        val postDate: Date,
        val postText:String,
        var liked: Boolean,
        var likesCount: Int,
        var shareCount: Int,
        var viewCount: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding см. build.gradle (app)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        //setContentView(R.layout.activity_main)
        // доступ по findViewById
        //val button = findViewById<ImageButton>(R.id.favoriteButton)

        // synthetic см. build.gradle (app)
        //val button = favoriteButton

        val post = Post(1,
                        "Аффтор",
                        Date(2022,8,16),
                       "text",
                       false,
                       1099,
                       680,
                       2656)

        setContentView(binding.root)

        with(binding) {
            authorName.text = post.authorName
            authorAvatarImageView.setImageResource(R.drawable.ic_avatar_24)
            postDate.text = post.postDate.toLocaleString()
            toString().
            postText.text = post.postText
            likeCount.text = toText(post.likesCount)
            shareCount.text = toText(post.shareCount)
            viewCount.text = toText(post.viewCount)

            likeButton.setOnClickListener {
                post.liked = !post.liked
                if (post.liked) {
                    post.likesCount++
                    likeButton.setImageResource(R.drawable.ic_liked_24)
                } else {
                    post.likesCount--
                    likeButton.setImageResource(R.drawable.ic_like_24)
                }
                likeCount.text = toText(post.likesCount)
            }

            shareButton.setOnClickListener {
                post.shareCount ++
                shareCount.text = toText(post.shareCount)
            }
        }
    }
}