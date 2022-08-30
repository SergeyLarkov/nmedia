package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.Post

class PostViewModel: ViewModel() {
    private val repository: PostRepository = PostRepositoryImpl()
    val data = repository.getAll()
    fun like(post: Post) = repository.like(post)
    fun share(post: Post) = repository.share(post)
}