package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import android.widget.Toast.makeText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.sql.PostDao
import ru.netology.nmedia.sqlRoom.PostEntity
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit
import java.io.IOException
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.api.PostsApiService
import ru.netology.nmedia.data.EMPTY_POST
import java.net.ConnectException

class PostRepositoryHttpImpl : PostRepository {



    override fun getAll(): List<Post> {


//        return client.newCall(request)
//            .execute()
//            .let { it.body?.string() ?: throw RuntimeException("body is null") }
//            .let { gson.fromJson(it, typeToken.type) }
        return emptyList()
    }

    override fun getAllAsync(callback: PostRepository.GetAllCallback) {
        PostsApi.retrofitService.getAll().enqueue(
            object : retrofit2.Callback<List<Post>> {
                override fun onResponse(
                    call: retrofit2.Call<List<Post>>,
                    response: retrofit2.Response<List<Post>>
                ) {
                    if (!response.isSuccessful) {
                        if (response.code().toString().first() != '2') {
                            getAllAsync(callback)//дз для баггисервера
                            return
                        }
                        callback.onError(RuntimeException(response.message()))
                        return
                    }

                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                    }

                    override fun onFailure(call: retrofit2.Call<List<Post>>, t: Throwable) {
                        callback.onError(ConnectException("Connection is lost"))
                }
            })
    }


    override fun getPostById(id: Long): Post {
        //val post1Element = posts.filter { it.id == id }
        //if (post1Element.size == 0) return null
        val posts = getAll()

        posts.filter { it.id == id }.first().let {
            return it
        }

        //return Post(0, "", "", "")
        return EMPTY_POST
    }

    override fun likeById(id: Long): Post {
        // dao.likeById(id)
//        val emptyPost = Post(0, "", "", "")
        val emptyPost = EMPTY_POST

//
//        val post = getPostById(id)
//
//        if (post.id == 0L) return emptyPost
//
//        if (post.likedByMe) {
//            val request: Request = Request.Builder()
//                .method("DELETE", "".toRequestBody())
//
//                .url("${BASE_URL}/api/posts/$id/likes")
//                .build()
//
//            client.newCall(request)
//                .execute()
//                .let { it.body?.string() ?: throw RuntimeException("body is null") }
//                .let {
//                    val pp = gson.fromJson(it, Post::class.java)
//                    return pp
//                }
//
//        } else {
//            val request: Request = Request.Builder()
//                .method("POST", "".toRequestBody())
//
//                .url("${BASE_URL}/api/posts/$id/likes")
//                .build()
//
//            client.newCall(request)
//                .execute()
//                .let { it.body?.string() ?: throw RuntimeException("body is null") }
//                .let {
//                    val pp = gson.fromJson(it, Post::class.java)
//                    return pp
//                }
//        }
//
//
        return emptyPost
    }

    override fun likeByIdAsync(
        id: Long,
        likedByMe: Boolean,
        callback: PostRepository.CallbackWithPostOnSuccess
    ) {
//        val request: Request = Request.Builder()
//            .method(if (likedByMe) "POST" else "DELETE", "".toRequestBody())
//            .url("${BASE_URL}/api/posts/$id/likes")
//            .build()
//
//        client.newCall(request)
//            .enqueue(object : Callback {
//                override fun onResponse(call: Call, response: Response) {
//
//                    val body = response.body?.string() ?: throw RuntimeException("body is null")
//                    val newPost = gson.fromJson(body, Post::class.java)
//
//                    try {
//                        callback.onSuccess(newPost)
//                    } catch (e: Exception) {
//                        callback.onError(e)
//                    }
//                }
//
//                override fun onFailure(call: Call, e: IOException) {
//                    callback.onError(e)
//                }
//            })
    }

    override fun likeByIdRetrofit(
        id: Long,
        likedByMe: Boolean,
        callback: PostRepository.CallbackWithPostOnSuccess
    ) {

        if (likedByMe) {
            PostsApi.retrofitService.likeById(id).enqueue(
                object : retrofit2.Callback<Post> {
                    override fun onResponse(
                        call: retrofit2.Call<Post>,
                        response: retrofit2.Response<Post>
                    ) {
                        if (!response.isSuccessful) {
                            if (response.code().toString().first() != '2') {
                                likeByIdRetrofit(id, likedByMe, callback)//дз для баггисервера
                                return
                            }
                            callback.onError(RuntimeException(response.message()))
                            return
                        }

                        callback.onSuccess(
                            response.body() ?: throw RuntimeException("body is null")
                        )
                    }

                    override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                        callback.onError(ConnectException("Connection is lost"))
                    }
                })
        } else {
            PostsApi.retrofitService.dislikeById(id).enqueue(
                object : retrofit2.Callback<Post> {
                    override fun onResponse(
                        call: retrofit2.Call<Post>,
                        response: retrofit2.Response<Post>
                    ) {
                        if (!response.isSuccessful) {
                            if (response.code().toString().first() != '2') {
                                likeByIdRetrofit(id, likedByMe, callback)//дз для баггисервера
                                return
                            }
                            callback.onError(RuntimeException(response.message()))
                            return
                        }

                        callback.onSuccess(
                            response.body() ?: throw RuntimeException("body is null")
                        )
                    }

                    override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                        callback.onError(ConnectException("Connection is lost"))
                    }
                })
        }

//        val request: Request = Request.Builder()
//            .method(if (likedByMe) "POST" else "DELETE", "".toRequestBody())
//            .url("${BASE_URL}/api/posts/$id/likes")
//            .build()
//
//        client.newCall(request)
//            .enqueue(object : Callback {
//                override fun onResponse(call: Call, response: Response) {
//
//                    val body = response.body?.string() ?: throw RuntimeException("body is null")
//                    val newPost = gson.fromJson(body, Post::class.java)
//
//                    try {
//                        callback.onSuccess(newPost)
//                    } catch (e: Exception) {
//                        callback.onError(e)
//                    }
//                }
//
//                override fun onFailure(call: Call, e: IOException) {
//                    callback.onError(e)
//                }
//            })
    }


    override fun shareById(id: Long) {
        //dao.shareById(id)
    }

    override fun save(post: Post, callback: PostRepository.CallbackWithPostOnSuccess) {
        val result = PostsApi.retrofitService.save(post)
            .enqueue(object : retrofit2.Callback<Post> {
                override fun onResponse(
                    call: retrofit2.Call<Post>,
                    response: retrofit2.Response<Post>
                ) {
                    if (!response.isSuccessful) {
                        if (response.code().toString().first() != '2') {
                            save(post, callback)//дз для баггисервера
                            return
                        }

                        callback.onError(RuntimeException(response.message()))
                        return
                    }

                    callback.onSuccess(
                        response.body() ?: throw RuntimeException("body is null")
                    )
                }

                override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                    callback.onError(ConnectException("Connection is lost"))
                }
            })
    }

    override fun saveAsync(post: Post, callback: PostRepository.CallbackWithNoParameters) {


        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        val gson = Gson()
        val typeToken = object : TypeToken<List<Post>>() {}
        //private val typeTokenPost = object : TypeToken<Post::class.java>(){}

//          companion object {
//              private const val BASE_URL = "http://10.0.2.2:9999"
//              private val jsonType = "application/json".toMediaType()
//          }

        val jsonType = "application/json".toMediaType()

        val request: Request = Request.Builder()
            .post(gson.toJson(post).toRequestBody(jsonType))
            .url("${BuildConfig.BASE_URL}posts")
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    println("!!!!!! Response on save post in PostRepositoryImpl")
                    callback.onSuccess()

                }

                override fun onFailure(call: Call, e: IOException) {
                    // Toast.makeText(this, "some error on save in repositoryImpl", Toast.LENGTH_LONG).show()
                    callback.onError(e)
                    println("!!!!!! error on save post in PostRepositoryImpl")
                }
            }
            )
    }

override fun removeById(id: Long) {
    val response = PostsApi.retrofitService.removeById(id)
        .execute()

}

override fun removeByIdAsync(id: Long, callback: PostRepository.CallbackWithNoParameters) {

//        val request: Request = Request.Builder()
//            .delete()
//            .url("${BASE_URL}/api/slow/posts/$id")
//            .build()
//
//        client.newCall(request)
//            .enqueue(object : Callback {
//                override fun onResponse(call: Call, response: Response) {
//                    println("!!!!!! Response on removeByIdAsync post in PostRepositoryImpl")
//                    callback.onSuccess()
//
//                }
//
//                override fun onFailure(call: Call, e: IOException) {
//                    // Toast.makeText(this, "some error on save in repositoryImpl", Toast.LENGTH_LONG).show()
//                    callback.onError(e)
//                    println("!!!!!! error on removeByIdAsync in PostRepositoryImpl")
//                }
//            }
//            )
}

override fun editById(id: Long, content: String) {
//        dao.updateContentById(id, content)

}


}