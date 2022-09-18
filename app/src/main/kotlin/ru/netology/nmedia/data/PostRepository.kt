package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepository {

    //val data: LiveData<Post>
    //fun get(): LiveData<Post>
    //fun like()
    //fun share()
    fun getAll(): LiveData<List<Post>>
    fun getPostById(id: Long): Post?
    fun likeById(id: Long)
    fun shareById(id: Long)

   fun removeById(id:Long)
   fun save(post: Post)
   fun editById(id: Long, content: String)



}