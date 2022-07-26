package ru.netology.nmedia

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepositoryImpl
import android.app.Application
import androidx.lifecycle.AndroidViewModel



private val empty = Post(
    0,
    "",
    "",
    "",
    0,
    0,
    0,
    false


)

//class PostViewModel : ViewModel() {
class PostViewModel(application: Application) : AndroidViewModel(application) {
//    private val repository: PostRepository = InMemoryPostRepositoryImpl()
    private val repository: PostRepository = InMemoryPostRepositoryImpl(application)
    val data = repository.getAll()
    val edited = MutableLiveData(empty)
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)

   fun removeById(id:Long) = repository.removeById(id)
    fun editById(id: Long, content: String) = repository.editById(id, content)

    fun save(){

        edited.value?.let {
            repository.save(it)

            edited.value = empty

        }



    }

    fun edit(post: Post){

        edited.value = post
    }

    fun changeContent(content: String) {
        if (content.isBlank()){
            return
        }

        edited.value?.let{
            edited.value = it.copy(content = content)
        }

    }

    fun cancelEdit(){
        edited.value = empty
    }
}