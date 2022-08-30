package ru.netology.nmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import java.util.*

class PostRepositoryImpl: PostRepository {
    private val posts = listOf(
        Post(1,
        "Аффтор",
        Date(2022,1,1,0,0,0),
        "С новым годом!",
        false,
        1099,
        680,
        2656),
        Post(2,
        "Аффтор",
        Date(2022,1,1,0,0,0),
        "Happy New Year!",
        false,
        0,
        0,
        0),
        Post(3,
        "Аффтор",
        Date(2022,1,1,0,0,0),
        "S Novim Godom!",
        false,
        0,
        0,
        0),
        Post(4,
            "Аффтор",
            Date(2022,1,1,0,0,0),
            "S Novim Godom!",
            false,
            0,
            0,
            0),
        Post(5,
            "Аффтор",
            Date(2022,1,1,0,0,0),
            "S Novim Godom!",
            false,
            0,
            0,
            0),
        Post(6,
            "Аффтор",
            Date(2022,1,1,0,0,0),
            "S Novim Godom!",
            false,
            0,
            0,
            0),
        Post(7,
            "Аффтор",
            Date(2022,1,1,0,0,0),
            "S Novim Godom!",
            false,
            0,
            0,
            0))

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>>  = data

    override fun like(post:Post) {
        val posts = data.value!!.map {
            if (it.id == post.id ) it.copy(
                liked = !it.liked,
                likesCount = if (!it.liked) it.likesCount + 1 else it.likesCount - 1
            ) else it
        }
        data.value = posts
    }

    override fun share(post:Post) {
        val posts = data.value!!.map { if (it.id == post.id ) it.copy(shareCount = it.shareCount + 1) else it }
        data.value = posts
    }

    override fun get(id: Long): Post? {
        return data.value!!.find { post: Post -> post.id == id }
    }

}