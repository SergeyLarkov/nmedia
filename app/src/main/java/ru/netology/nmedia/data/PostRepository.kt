package ru.netology.nmedia.data

import androidx.lifecycle.LiveData

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun get(id: Long): Post?
    fun like(post: Post)
    fun share(post: Post)
}