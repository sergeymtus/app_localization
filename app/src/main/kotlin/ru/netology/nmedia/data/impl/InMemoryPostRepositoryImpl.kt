package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.Post

class InMemoryPostRepositoryImpl : PostRepository {


    private var post = Post(
        1,
        "Нетология. Университет интернет-профессий будущего",
        "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        "25 июня в 18:00",
        999,
        998,
        1000000,
        false
    )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data
    override fun like() {
        post = post.copy(likedByMe = !post.likedByMe)
        if (post.likedByMe) {
            post = post.copy(likesCount = post.likesCount + 1)
        } else {
            post = post.copy(likesCount = post.likesCount - 1)
        }
        data.value = post
    }

    override fun share() {
        post = post.copy(shareCountValue = post.shareCountValue + 1)
        data.value = post
    }
}