package ru.netology.nmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository

class MemoryPostRepositoryImpl(posts:List<Post>): PostRepository {
    private val data = MutableLiveData(posts)
    private var newPostId: Long = 1000L

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

    override fun delete(post:Post) {
        val posts = data.value!!.filter { it.id !=post.id }
        data.value = posts
    }

    override fun new(post:Post) {
        data.value = listOf(post.copy(id = newPostId)) + data.value!!
        newPostId++
    }

    override fun edit(postId: Long, text: String) {
        val posts = data.value!!.map { if (it.id == postId) it.copy(postText = text) else it }
        data.value = posts
    }
}