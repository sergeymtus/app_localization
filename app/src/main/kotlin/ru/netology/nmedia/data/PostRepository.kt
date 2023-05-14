package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {




    fun getAll(): List<Post>
    fun getPostById(id: Long): Post
    fun likeById(id: Long):Post
    fun shareById(id: Long)
    fun save(post: Post, callback: CallbackWithPostOnSuccess)
    fun removeById(id: Long)
    fun editById(id: Long, content: String)

    fun getAllAsync(callback: GetAllCallback)
    fun likeByIdAsync(id: Long, likedByMe: Boolean, callback: CallbackWithPostOnSuccess)
    fun likeByIdRetrofit(id: Long, likedByMe: Boolean, callback: CallbackWithPostOnSuccess)
    fun saveAsync(post: Post, callback: CallbackWithNoParameters)
    fun removeByIdAsync(id: Long, callback: CallbackWithNoParameters)

    interface GetAllCallback {
        fun onSuccess(posts: List<Post>) {}
        fun onError(e: Exception) {}
    }

    interface CallbackWithNoParameters {
        fun onSuccess() {}
        fun onError(e: Exception) {}
    }

    interface CallbackWithPostOnSuccess {
        fun onSuccess(post: Post) {}
        fun onError(e: Exception) {}
    }


}