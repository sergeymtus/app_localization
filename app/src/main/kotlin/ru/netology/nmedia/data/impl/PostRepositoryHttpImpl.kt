package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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
import ru.netology.nmedia.data.EMPTY_POST

class PostRepositoryHttpImpl : PostRepository {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()
    private val typeToken = object : TypeToken<List<Post>>() {}
    private val typeTokenPost = object : TypeToken<Post>(){}

    companion object {
        private const val BASE_URL = "http://10.0.2.2:9999"
        private val jsonType = "application/json".toMediaType()
    }


    override fun getAll(): List<Post> {
        val request: Request = Request.Builder()
            .url("${BASE_URL}/api/slow/posts")
            .build()

        return client.newCall(request)
            .execute()
            .let { it.body?.string() ?: throw RuntimeException("body is null") }
            .let { gson.fromJson(it, typeToken.type) }
    }

    override fun getAllAsync(callback: PostRepository.GetAllCallback) {
        val request: Request = Request.Builder()
            .url("${BASE_URL}/api/slow/posts")
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string() ?: throw RuntimeException("body is null")
                    try {
                        callback.onSuccess(gson.fromJson(body, typeToken.type))
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(e)
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

        val post = getPostById(id)

        if (post.id == 0L) return emptyPost

        if (post.likedByMe) {
            val request: Request = Request.Builder()
                .method("DELETE", "".toRequestBody())

                .url("${BASE_URL}/api/posts/$id/likes")
                .build()

            client.newCall(request)
                .execute()
                .let { it.body?.string() ?: throw RuntimeException("body is null") }
                .let {
                    val pp = gson.fromJson(it, Post::class.java)
                    return pp
                }

        } else {
            val request: Request = Request.Builder()
                .method("POST", "".toRequestBody())

                .url("${BASE_URL}/api/posts/$id/likes")
                .build()

            client.newCall(request)
                .execute()
                .let { it.body?.string() ?: throw RuntimeException("body is null") }
                .let {
                    val pp = gson.fromJson(it, Post::class.java)
                    return pp
                }
        }
        return emptyPost
    }

    override fun likeByIdAsync(
        id: Long,
        likedByMe: Boolean,
        callback: PostRepository.CallbackWithPostOnSuccess
    ) {

        val request: Request = Request.Builder()
            .method(if (likedByMe) "POST" else "DELETE", "".toRequestBody())
            .url("${BASE_URL}/api/posts/$id/likes")
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {

                    val body = response.body?.string() ?: throw RuntimeException("body is null")
                    val newPost = gson.fromJson(body, Post::class.java)

                    try {
                        callback.onSuccess(newPost)
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(e)
                }
            })
    }


    override fun shareById(id: Long) {
        //dao.shareById(id)
    }

    override fun save(post: Post) {
        //До асинхронов+
        val request: Request = Request.Builder()
            .post(gson.toJson(post).toRequestBody(jsonType))
            .url("${BASE_URL}/api/slow/posts")
            .build()

        client.newCall(request)
            .execute()
            .close()
        //До асинхронов-
    }

    override fun saveAsync(post: Post, callback: PostRepository.CallbackWithNoParameters) {

        val request: Request = Request.Builder()
            .post(gson.toJson(post).toRequestBody(jsonType))
            .url("${BASE_URL}/api/slow/posts")
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
        val request: Request = Request.Builder()
            .delete()
            .url("${BASE_URL}/api/slow/posts/$id")
            .build()

        client.newCall(request)
            .execute()
            .close()
    }

    override fun removeByIdAsync(id: Long, callback: PostRepository.CallbackWithNoParameters) {

        val request: Request = Request.Builder()
            .delete()
            .url("${BASE_URL}/api/slow/posts/$id")
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    println("!!!!!! Response on removeByIdAsync post in PostRepositoryImpl")
                    callback.onSuccess()

                }

                override fun onFailure(call: Call, e: IOException) {
                    // Toast.makeText(this, "some error on save in repositoryImpl", Toast.LENGTH_LONG).show()
                    callback.onError(e)
                    println("!!!!!! error on removeByIdAsync in PostRepositoryImpl")
                }
            }
            )
    }

    override fun editById(id: Long, content: String) {
//        dao.updateContentById(id, content)

    }


}