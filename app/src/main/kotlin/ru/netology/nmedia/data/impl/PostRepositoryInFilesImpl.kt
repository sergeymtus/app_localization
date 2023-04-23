package ru.netology.nmedia.data.impl

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post

class PostRepositoryInFilesImpl (
    private val context: Context
) : PostRepository {

//    private var post:Post = Post(
//        id = 1,
//        author = "Нетология. Университет интернет-профессий будущего",
//        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработки, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов.",
//        published = "20 мая в 19:13",
//        likes = 999,
//        shares = 985
//    )

    //    private val postKey = "posts"
//    private val prefs = context.getSharedPreferences(postKey, Context.MODE_PRIVATE)
    private val gson = Gson()
    private val typeToken = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "posts.json"
    private var posts = readAll()
        set(value) {
            field = value
            sync()
        }


    private fun sync() {
//        prefs.edit {
//            val postsString = gson.toJson(posts, typeToken)
//            putString(postKey, postsString)
//        }

        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts, typeToken))
        }
    }

    private fun readAll(): List<Post> {
        val file = context.filesDir.resolve(filename)
        return if (file.exists()) {
            context.openFileInput(filename).bufferedReader().use{
                gson.fromJson(it, typeToken)
            }
        } else {
            emptyList()
        }

//        prefs.getString(postKey, null).let {
//            gson.fromJson<List<Post>>(it, typeToken)
//        }.orEmpty()*/
    }



//     private var posts =
//        listOf(
//            Post(
//                id = 2,
//                author = "Нетология. Университет интернет-профессий будущего",
//                content = "Знаний хватит на всех: разберемся!",
//                published = "30 мая в 23:13",
//                likes = 2,
//                shares = 3
//            ),
//            Post(
//                id = 1,
//                author = "Нетология. Университет интернет-профессий будущего",
//                content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработки, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов.",
//                published = "20 мая в 19:13",
//                likes = 999,
//                shares = 985,
//                video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
//            ),
//            Post(
//                id = 3,
//                author = "33Нетология. Университет интернет-профессий будущего",
//                content = "33Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработки, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов.",
//                published = "3320 мая в 19:13",
//                likes = 999,
//                shares = 985
//            ),
//
//
//            )
//    set(value) {
//        field = value
//        sync()
//    }


    private val data = MutableLiveData(posts)
    override fun getAll(): LiveData<List<Post>> = data

//    override fun like() {
//
//        post = post.copy(
//            likes= post.likes + if (post.likedByMe) -1 else 1,
//            likedByMe = !post.likedByMe
//        )
//
//        data.value = post
//
//    }
//
//    override fun share() {
//        post = post.copy(
//            shares = post.shares + 1
//        )
//
//        data.value = post
//    }

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else
                if (it.likedByMe) it.copy(likedByMe = !it.likedByMe, likes = it.likes - 1)
                else it.copy(likedByMe = !it.likedByMe, likes = it.likes + 1)
        }

        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shares = it.shares + 1)
        }

        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun editById(id: Long, content: String) {

        posts = posts.map {
            if (it.id != id) it else it.copy(content = content)
        }

        data.value = posts
    }

    override fun save(post: Post) {
        var lastId = posts.firstOrNull()?.id ?: 0L
        lastId++

        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = lastId,
                    author = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + posts
            data.value = posts
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }

        data.value = posts
    }

    override fun getPostById(id: Long): Post {
        val post1Element = posts.filter { it.id == id }
        //if (post1Element.size == 0) return null

        return post1Element.first()
    }


}