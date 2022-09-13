package ru.netology.nmedia.db

import ru.netology.nmedia.data.Post

interface PostDao {
    fun getAll(): List<Post>
    fun getMaxPostId(): Long
    fun like(postId: Long)
    fun share(postId: Long)
    fun delete(postId: Long)
    fun new(post:Post)
    fun edit(postId: Long, text: String)
}