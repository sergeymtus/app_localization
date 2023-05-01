package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.sql.PostDao
import ru.netology.nmedia.sqlRoom.PostEntity
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

class PostRepositoryHttpImpl:PostRepository {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()
    private val typeToken = object : TypeToken<List<Post>>(){}
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
            .let{ it.body?.string() ?: throw RuntimeException("body is null")}
            .let{gson.fromJson(it, typeToken.type)}
    }

    override fun getPostById(id: Long): Post {
        //val post1Element = posts.filter { it.id == id }
        //if (post1Element.size == 0) return null
        val posts = getAll()

        posts.filter { it.id == id }.first().let {
            return it
        }

        //return Post(0, "", "", "")
    }

    override fun likeById(id: Long):Post {
        // dao.likeById(id)
        val emptyPost = Post(0, "", "", "")

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

    override fun shareById(id: Long) {
        //dao.shareById(id)
    }

    override fun save(post: Post) {
        val request: Request = Request.Builder()
            .post(gson.toJson(post).toRequestBody(jsonType))
            .url("${BASE_URL}/api/slow/posts")
            .build()

        client.newCall(request)
            .execute()
            .close()
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

    override fun editById(id: Long, content: String) {
//        dao.updateContentById(id, content)

    }


}