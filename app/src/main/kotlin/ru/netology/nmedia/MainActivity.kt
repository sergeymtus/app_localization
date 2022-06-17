package ru.netology.nmedia

import ru.netology.nmedia.Post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            1,
            "Нетология. Университет интернет-профессий будущего",
            "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            "09 мая в 18:00",
            16,
            10000001
        )
            .apply { }

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.text = post.likesCount.toString()
            shares.text = post.sharesCount.toString()

            if (post.likedByMe) {
                like?.setImageResource(R.drawable.ic_liked_24)
            }

            like?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
                if (post.likedByMe) {
                    post.likesCount += 1
                    likes.text = post.likesCount.toString()
                } else {
                    post.likesCount -= 1
                    likes.text = post.likesCount.toString()
                }
            }

            share?.setOnClickListener {
                post.sharesCount += 1
                shares.text = countNumber(post.sharesCount)
            }


        }


    }

    fun countNumber(count: Int): String {
        val count = count
        var converted: String
        converted = count.toString()
        when (count) {
            in 0..999 -> converted = count.toString()
            in 1000..9999 -> if ((count % 1000) < 100) {
                converted = ((count / 1000).toString() + "K")
            } else {
                converted =
                    ((count / 1000).toString() + "," + ((count % 1000) / 100).toString() + "K")
            }
            in 10000..999999 -> converted = ((count / 1000).toString() + "K")
            in 1000000..Long.MAX_VALUE -> converted = ((count / 1000000).toString() + "M")
        }
        return converted
    }
}
