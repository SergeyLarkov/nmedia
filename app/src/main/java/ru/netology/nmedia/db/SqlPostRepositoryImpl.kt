package ru.netology.nmedia.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository


class SqlPostRepositoryImpl(private val dao: PostDao): PostRepository {
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        posts = dao.getAll()
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun like(post:Post) {
        posts = posts.map {
            if (it.id == post.id ) it.copy(
                liked = !it.liked,
                likesCount = if (!it.liked) it.likesCount + 1 else it.likesCount - 1
            ) else it
        }
        data.value = posts
        dao.like(post.id)
    }

    override fun share(post:Post) {
        posts = posts.map { if (it.id == post.id ) it.copy(shareCount = it.shareCount + 1) else it }
        data.value = posts
        dao.share(post.id)
    }

    override fun get(id: Long): Post? {
        return posts.find { post: Post -> post.id == id }
    }

    override fun delete(post:Post) {
        posts = posts.filter { it.id !=post.id }
        data.value = posts
        dao.delete(post.id)
    }

    override fun new(post:Post) {
        val newPost = post.copy(id = dao.getMaxPostId() + 1L)
        posts = listOf(newPost) + posts
        data.value = posts
        dao.new(newPost)
    }

    override fun edit(postId: Long, text: String) {
        posts = posts.map { if (it.id == postId) it.copy(postText = text) else it }
        data.value = posts
        dao.edit(postId, text)
    }

}