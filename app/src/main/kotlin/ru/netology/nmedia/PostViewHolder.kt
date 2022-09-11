package ru.netology.nmedia

import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
//import ru.netology.R
//import ru.netology.databinding.CardPostBinding
import ru.netology.nmedia.Post
import ru.netology.nmedia.databinding.CardPostBinding


class PostViewHolder(
    private val binding: CardPostBinding,
//    private val onLikeListener: OnLikeListener,
//    private val onShareListener: OnShareListener,
//    private val onRemoveListener: OnRemoveListener
    private val listener: PostInteractionListener

) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
 //           likes.text = countNumber(post.likesCount)
            like.text = countNumber(post.likesCount)
//            shares.text = countNumber(post.shareCountValue)
            share.text = countNumber(post.shareCountValue)
            views.text = post.viewCountValue.toString()

            like.isChecked = post.likedByMe
//            like.setImageResource(
//                if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
//            )
            if (post.video.isNullOrBlank()) groupVideo.visibility = View.GONE else groupVideo.visibility = View.VISIBLE


            menu.setOnClickListener{
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_menu)

                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
//                                onRemoveListener(post)
                                true
                            }

                            R.id.edit ->{
                                listener.onEdit(post)
                                true

                            }
                            else -> false
                        }

                    }

                    show()
                }
            }


            like.setOnClickListener {
                listener.onLike(post)
            }
            share.setOnClickListener {
                listener.onShare(post)
            }


            videoPlayButton.setOnClickListener{
                listener.onVideoLink(post)
            }

            videoImage.setOnClickListener {
                listener.onVideoLink(post)
            }

            rootXmlElement.setOnClickListener {
                listener.onOpenPost(post)
            }


            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_menu)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
                                //onRemoveListener(post)
                                true
                            }
                            R.id.edit -> {
                                listener.onEdit(post)
                                true
                           }
                            else -> false
                        }

                    }
                }.show()
            }
        }
    }
}