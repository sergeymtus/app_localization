package ru.netology.nmedia

import androidx.recyclerview.widget.RecyclerView
//import ru.netology.R
//import ru.netology.databinding.CardPostBinding
import ru.netology.nmedia.Post
import ru.netology.nmedia.databinding.CardPostBinding


class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.text = countNumber(post.likesCount)
            shares.text = countNumber(post.shareCountValue)
            views.text = post.viewCountValue.toString()
            like.setImageResource(
                if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
            )
            like.setOnClickListener {
                onLikeListener(post)
            }
            share.setOnClickListener {
                onShareListener(post)
            }
        }
    }
}