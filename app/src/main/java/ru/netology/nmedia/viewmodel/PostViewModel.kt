package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.netology.nmedia.data.*
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.db.SqlPostRepositoryImpl
import java.util.*

class PostViewModel(application: Application): AndroidViewModel(application), ClickEvents {
    private val repository:PostRepository = SqlPostRepositoryImpl(
            dao = AppDb.getInstance(application).postDao
    )

    val data = repository.getAll()

    val textToShare = SingleLiveEvent<String>()
    val editEvent = SingleLiveEvent<Unit>()
    val videoEvent = SingleLiveEvent<String>()

    var postToEdit: Post = EMPTY_POST

    override fun onLikeClicked(post: Post) = repository.like(post)

    override fun onShareClicked(post: Post) {
        repository.share(post)
        textToShare.value = post.postText
    }

    override fun onDeleteClicked(post: Post) = repository.delete(post)

    override fun onEditClicked(post: Post) {
        postToEdit = post
        editEvent.call()
    }

    override fun onAddClicked() {
        postToEdit = EMPTY_POST
        editEvent.call()
    }

    override fun onVideoClicked(post: Post) {
        if (post.urlVideo.isNotBlank()) {
            videoEvent.value = post.urlVideo
        }
    }

    fun savePostContent(postContent: String) {
        if (postToEdit.id == 0L) {
            repository.new(
                EMPTY_POST.copy(authorName = "Me", postDate = Date().toString(), postText = postContent)
            )
        } else {
            repository.edit(
                postToEdit.id,
                postContent
            )
        }
        postToEdit = EMPTY_POST
    }
}