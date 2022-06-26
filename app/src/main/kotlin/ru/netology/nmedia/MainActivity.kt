package ru.netology.nmedia

import ru.netology.nmedia.Post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.CardPostBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel: PostViewModel by viewModels()

//        viewModel.data.observe(this) { posts ->
//            binding.container.removeAllViews()
//            posts.map { post ->
//                CardPostBinding.inflate(layoutInflater, binding.container, true).apply {
//                    author.text = post.author
//                    published.text = post.published
//                    content.text = post.content
//                    likes.text = countNumber(post.likesCount)
//                    shares.text = countNumber(post.shareCountValue)
//                    views.text = countNumber(post.viewCountValue)
//
//                    like.setImageResource(
//                        if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
//                    )
//
//                    like?.setOnClickListener {
//                        viewModel.likeById(post.id)
//                    }
//                    share.setOnClickListener {
//                        viewModel.shareById(post.id)
//                    }
//
//                }.root
//            }
//        }

        val adapter = PostsAdapter(
            { viewModel.likeById(it.id) },
            { viewModel.shareById(it.id) }
        )

        binding.list.adapter = adapter
        viewModel.data.observe(this){ posts ->
            //adapter.List = posts
            adapter.submitList(posts)
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
