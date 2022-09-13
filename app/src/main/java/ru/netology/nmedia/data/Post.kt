package ru.netology.nmedia.data

val EMPTY_POST = Post(0,"", "","", "", false,0,0,0)

data class Post(
    val id: Long,
    val authorName: String,
    val postDate: String,
    val postText:String,
    val urlVideo:String,
    val liked: Boolean,
    val likesCount: Int,
    val shareCount: Int,
    val viewCount: Int)