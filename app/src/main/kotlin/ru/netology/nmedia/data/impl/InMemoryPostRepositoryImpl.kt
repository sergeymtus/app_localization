package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.Post

class InMemoryPostRepositoryImpl : PostRepository {


    private var posts = listOf(
        Post(
            4,
            "Нетология. Университет интернет-профессий будущего",
            "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            "25 июня в 18:00",
            999,
            998,
            1000000,
            false
        ),

        Post (
            3,
            "Второй пост",
            "Это текст второго поста. Он будет написан здесь.Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен",
            "28 июня в 18:00",
            998,
            999,
            5000,
            false,
            "https://www.youtube.com/watch?v=WhWc3b3KhnY"


        ),
        Post (
            2,
            "Второй пост",
            "Это текст третьего поста. Он будет написан здесь. Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее",
            "28 июня в 18:00",
            1050,
            999,
            5001,
            false
        ),
        Post (
            1,
            "Третий пост",
            "Это текст четвертого поста. Он будет написан здесь. Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее",
            "28 июня в 18:00",
            1050,
            999,
            5002,
            false
        )
    )

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

//    override fun like() {
//        post = post.copy(likedByMe = !post.likedByMe)
//        if (post.likedByMe) {
//            post = post.copy(likesCount = post.likesCount + 1)
//        } else {
//            post = post.copy(likesCount = post.likesCount - 1)
//        }
//        data.value = post
//    }


    override fun editById(id: Long, content: String) {

        posts = posts.map {
            if (it.id != id) it else it.copy(content = content)
        }

        data.value = posts
    }

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else
                if (it.likedByMe) it.copy(likedByMe = !it.likedByMe, likesCount = it.likesCount - 1)
                else it.copy(likedByMe = !it.likedByMe, likesCount = it.likesCount + 1)
        }

        data.value = posts
    }


//    override fun share() {
//        post = post.copy(shareCountValue = post.shareCountValue + 1)
//        data.value = post
//    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shareCountValue = it.shareCountValue + 1)
        }

        data.value = posts
    }

    override fun  removeById(id:Long){
        posts = posts.filterNot{it.id == id}
        data.value = posts
    }

    override fun save(post: Post) {

        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = posts.firstOrNull()?.id?.plus(1) ?: 0L
                )
            ) + posts
            data.value = posts
            return
        }

            posts = posts.map{
                if (it.id == post.id) {
                    it.copy(content = post.content)
                } else {
                    it
                }

            }
        data.value = posts

    }


}