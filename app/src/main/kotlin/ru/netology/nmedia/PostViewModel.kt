package ru.netology.nmedia

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepositoryImpl

class PostViewModel : ViewModel() {
    private val repository: PostRepository = InMemoryPostRepositoryImpl()
    val data = repository.get()
    fun onLikeClicked() = repository.like()
    fun onShareClicked() = repository.share()
}