package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.PostRepositoryInFilesImpl
import ru.netology.nmedia.data.impl.PostRepositorySQLiteImpl
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.sql.AppDb

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    published = ""
)

//class PostViewModel : ViewModel() {
class PostViewModel(application: Application) : AndroidViewModel(application) {

    //Упрощенный вариант создания репозитория
//    private val repository:PostRepository = PostRepositoryInMemoryImpl()
//
//    val data get() = repository.data
//
//    fun onClickLike() = repository.like()
//    fun onClickShare() = repository.share()

    //Упрощенный вариант
    //private val repository: PostRepository = PostRepositoryInFilesImpl(application)
    private val repository: PostRepository = PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao()
    )
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)
    fun editById(id: Long, content: String) = repository.editById(id, content)

    fun save() {
        edited.value?.let {
            repository.save(it)

            edited.value = empty
        }

    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        if (content.isBlank()){
            return
        }

        edited.value?.let {
            edited.value = it.copy(content = content)
        }
    }

    fun getPostById(id: Long): Post {
        val post = repository.getPostById(id)
        //if (post == null) return empty

        return post
    }
}