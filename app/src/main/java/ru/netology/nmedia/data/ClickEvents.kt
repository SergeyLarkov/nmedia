package ru.netology.nmedia.data

interface ClickEvents {
    fun onLikeClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onDeleteClicked(post: Post)
    fun onEditClicked(post: Post)
    fun onAddClicked()
}