package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post
import androidx.lifecycle.LiveData
import ru.netology.nmedia.data.impl.PostRepositoryHttpImpl
import ru.netology.nmedia.util.SingleLiveEvent
import java.io.IOException
import kotlin.concurrent.thread


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
    private val repository: PostRepository = PostRepositoryHttpImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated


    init {
        loadPosts()
    }


    fun loadPosts() {
        thread {
            //Start our loading
            _data.postValue(FeedModel(loading = true))
            try {
                val posts = repository.getAll()
                FeedModel(posts = posts, empty = posts.isEmpty())
            } catch (e: IOException) {
                FeedModel(error = true)
            }.also(_data::postValue)
        }
    }

    fun likeById(id: Long) {

        thread {

            val updatedPost = repository.likeById(id)


            val adf = 1

            if (updatedPost.id == 0L) return@thread


            val oldPosts = _data.value?.posts.orEmpty()
            val newPosts = oldPosts.map {
                if (it.id == id) updatedPost else it
            }
            _data.postValue(FeedModel(posts = newPosts))
        }
    }


    fun shareById(id: Long) = repository.shareById(id)

    fun removeById(id: Long) {
        thread {
            //Оптимистичная модель
            val old = _data.value?.posts.orEmpty()
            _data.postValue(
                _data.value?.copy(posts = _data.value?.posts.orEmpty()
                    .filter { it.id != id}
                )
            )
            try {
                repository.removeById(id)
            } catch (e : IOException) {
                _data.postValue(_data.value?.copy(posts = old))
            }
        }
    }
    fun editById(id: Long, content: String) = repository.editById(id, content)

    fun save() {
        edited.value?.let {
            thread {
                repository.save(it)
                _postCreated.postValue(Unit)
            }
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        if (content.isBlank()) {
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