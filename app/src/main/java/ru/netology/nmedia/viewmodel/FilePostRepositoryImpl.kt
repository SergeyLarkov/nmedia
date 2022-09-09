package ru.netology.nmedia.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository

class FilePostRepositoryImpl(private var context: Context): PostRepository {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val postsFileName = "posts.json"
    private val postCountFileName = "posts.dat"
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)
    private var newPostId = 1000L

    init {
        if (context.filesDir.resolve(postsFileName).exists()) {
            context.openFileInput(postsFileName).bufferedReader().use {
                posts = gson.fromJson(it, type)
            }
        } else {
            posts = emptyList()
        }
        data.value = posts

        if (context.filesDir.resolve(postCountFileName).exists()) {
            context.openFileInput(postCountFileName).use { stream ->
                val bufSize = stream.read()
                if (bufSize > 0) {
                    val buf = ByteArray(bufSize)
                    stream.read(buf)
                    val s = String(buf)
                    try {
                        newPostId = s.toLong()
                    } catch (e: NumberFormatException) {}
                }
            }
        }
    }

    private fun sync() {
        context.openFileOutput(postsFileName, Context.MODE_PRIVATE).bufferedWriter().use {
          it.write(gson.toJson(posts))
        }
        context.openFileOutput(postCountFileName, Context.MODE_PRIVATE).use {
            val b = newPostId.toString().toByteArray()
            it.write(b.size)
            it.write(b)
        }
    }

    override fun getAll(): LiveData<List<Post>>  = data

    override fun like(post:Post) {
        posts = posts.map {
            if (it.id == post.id ) it.copy(
                liked = !it.liked,
                likesCount = if (!it.liked) it.likesCount + 1 else it.likesCount - 1
            ) else it
        }
        data.value = posts
        sync()
    }

    override fun share(post:Post) {
        posts = posts.map { if (it.id == post.id ) it.copy(shareCount = it.shareCount + 1) else it }
        data.value = posts
        sync()
    }

    override fun get(id: Long): Post? {
        return posts.find { post: Post -> post.id == id }
    }

    override fun delete(post:Post) {
        posts = posts.filter { it.id !=post.id }
        data.value = posts
        sync()
    }

    override fun new(post:Post) {
        posts = listOf(post.copy(id = newPostId)) + posts
        data.value = posts
        newPostId++
        sync()
    }

    override fun edit(postId: Long, text: String) {
        posts = posts.map { if (it.id == postId) it.copy(postText = text) else it }
        data.value = posts
        sync()
    }
}