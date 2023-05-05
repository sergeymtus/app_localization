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


//    fun loadPosts() {
//        thread {
//            //Start our loading
//            _data.postValue(FeedModel(loading = true))
//            try {
//                val posts = repository.getAll()
//                FeedModel(posts = posts, empty = posts.isEmpty())
//            } catch (e: IOException) {
//                FeedModel(error = true)
//            }.also(_data::postValue)
//        }
//    }

    fun loadPosts() {
        _data.value = FeedModel(loading = true)
        repository.getAllAsync(object : PostRepository.GetAllCallback {
            override fun onSuccess(posts: List<Post>) {
                _data.postValue(FeedModel(posts = posts, empty = posts.isEmpty()))
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

    fun likeById(id: Long) {

//        thread {
//
//            val updatedPost = repository.likeById(id)
//
//
//            val adf = 1
//
//            if (updatedPost.id == 0L) return@thread
//
//
//            val oldPosts = _data.value?.posts.orEmpty()
//            val newPosts = oldPosts.map {
//                if (it.id == id) updatedPost else it
//            }
//            _data.postValue(FeedModel(posts = newPosts))
//        }

        val oldPosts = _data.value?.posts.orEmpty()
        val oldPost = oldPosts.filter { id == it.id }.first()

        repository.likeByIdAsync(id, !oldPost.likedByMe,
            object : PostRepository.CallbackWithPostOnSuccess {
                override fun onSuccess(post: Post) {
                    println("!!!!!!! onSuccess in likeById in viewModel")
                    val newPosts = oldPosts.map {
                        if (it.id == id) post else it
                    }
                    _data.postValue(FeedModel(posts = newPosts))
                }

                override fun onError(e: Exception) {
                    println("!!!!!!! onSuccess in likeById in viewModel")
                }
            }
                )



    }


    fun shareById(id: Long) = repository.shareById(id)

    fun removeById(id: Long) {
//        //Пример с синхронным вызвом+
//        thread {
//            //Оптимистичная модель
//            val old = _data.value?.posts.orEmpty()
//            _data.postValue(
//                _data.value?.copy(posts = _data.value?.posts.orEmpty()
//                    .filter { it.id != id}
//                )
//            )
//            try {
//                repository.removeById(id)
//            } catch (e : IOException) {
//                _data.postValue(_data.value?.copy(posts = old))
//            }
//        }

        try {
            repository.removeByIdAsync(id,
                object : PostRepository.CallbackWithNoParameters {
                    override fun onSuccess() {

                        _data.postValue(
                            _data.value?.copy(posts = _data.value?.posts.orEmpty()
                                .filter { it.id != id }
                            )
                        )
                        println("!!!!!!! onSuccess in removeById in viewModel")
                    }

                    override fun onError(e: Exception) {
                        val old = _data.value?.posts.orEmpty()
                        _data.postValue(_data.value?.copy(posts = old))
                        println("!!!!!!! onError in removeById in viewModel")
                    }
                }
            )
        } catch (e: IOException) {

        }
    }


    fun editById(id: Long, content: String) = repository.editById(id, content)

    fun save() {
        _data.value = FeedModel(loading = true)
        edited.value?.let {
            repository.saveAsync(it, object : PostRepository.CallbackWithNoParameters {
                override fun onSuccess() {
                    _postCreated.postValue(Unit)
                    println("!!!!!!!Success on save in viewModel!!!!!!!!!")
                }

                override fun onError(e: Exception) {
                    println("!!!!!!!error on save post in viewModel!!!!!!!!!")
                }
            })
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