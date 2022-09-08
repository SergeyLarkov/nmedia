package ru.netology.nmedia.data

import java.util.*

val EMPTY_POST = Post(0,"", Date(0L),"",false,0,0,0)

data class Post(
    val id: Long,
    val authorName: String,
    val postDate: Date,
    val postText:String,
    val liked: Boolean,
    val likesCount: Int,
    val shareCount: Int,
    val viewCount: Int)