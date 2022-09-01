package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.ClickEvents
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.getPostsList
import ru.netology.nmedia.showKeyboard
import java.util.*

class PostViewModel: ViewModel(), ClickEvents {
    private val repository: PostRepository = PostRepositoryImpl(getPostsList())
    val data = repository.getAll()
    private val emptyPost = Post(0,"", Date(0L),"",false,0,0,0)
    var editPost = MutableLiveData<Post>(emptyPost)

    fun setEditPostEmpty() { editPost.value = emptyPost }
    fun isNewPost(): Boolean {
        return editPost.value == emptyPost
    }

    override fun onLikeClicked(post: Post) = repository.like(post)

    override fun onShareClicked(post: Post) = repository.share(post)

    override fun onDeleteClicked(post: Post) {
        if (post != editPost.value) { repository.delete(post)}
    }

    override fun onEditClicked(post: Post) {
        editPost.value  = post
    }

    fun new(post: Post) =  repository.new(post)

    fun edit(postId: Long, text: String) =  repository.edit(postId,text)

}