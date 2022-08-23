package ru.netology.nmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import java.util.*

class PostRepositoryImpl: PostRepository {
    private val post = Post(1,
        "Аффтор",
        Date(2022,1,1,0,0,0),
        "С новым годом!",
        false,
        1099,
        680,
        2656)

    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post>  = data

    override fun like() {
        val post = data.value
        if (post != null) {
            data.value = post.copy(
                liked = !post.liked,
                likesCount = if (!post.liked) post.likesCount + 1 else post.likesCount - 1
            )
        }
    }

    override fun share() {
        val post = data.value
        if (post != null) {
            data.value = post.copy(shareCount = post.shareCount + 1)
        }
    }

}