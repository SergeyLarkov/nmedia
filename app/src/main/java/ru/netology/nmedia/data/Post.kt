package ru.netology.nmedia.data

import java.util.*

data class Post(
    val id: Int,
    val authorName: String,
    val postDate: Date,
    val postText:String,
    val liked: Boolean,
    val likesCount: Int,
    val shareCount: Int,
    val viewCount: Int)