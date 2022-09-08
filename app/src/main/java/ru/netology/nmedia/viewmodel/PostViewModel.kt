package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.*
import ru.netology.nmedia.getPostsList
import java.util.*

class PostViewModel: ViewModel(), ClickEvents {
    private val repository: PostRepository = PostRepositoryImpl(getPostsList())
    val data = repository.getAll()

    val textToShare = SingleLiveEvent<String>()
    val editEvent = SingleLiveEvent<Unit>()

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

    fun savePostContent(postContent: String) {
        if (postToEdit.id == 0L) {
            repository.new(
                EMPTY_POST.copy(authorName = "Me", postDate = Date(), postText = postContent)
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