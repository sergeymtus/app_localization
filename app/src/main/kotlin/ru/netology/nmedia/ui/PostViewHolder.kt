package ru.netology.nmedia.ui

import android.system.Os.remove
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.ui.OnInteractionListener
import com.bumptech.glide.Glide
import ru.netology.nmedia.data.BASE_URL


//import ru.netology.nmedia.ui.extraViewFunctions

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            // likesText.text = extraViewFunctions().stringNumberWithKInsteadOf1000(post.likes)
            like.text = extraViewFunctions().stringNumberWithKInsteadOf1000(post.likes)
            //sharesText.text = extraViewFunctions().stringNumberWithKInsteadOf1000(post.shares)
            share.text = extraViewFunctions().stringNumberWithKInsteadOf1000(post.shares)
//            like.setImageResource(
//                if (post.likedByMe) R.drawable.ic_like_24 else R.drawable.ic_like_border_24
//            )
            like.isChecked = post.likedByMe

            if (post.video.isNullOrBlank()) groupVideo.visibility = View.GONE else groupVideo.visibility = View.VISIBLE

            like.setOnClickListener {
                listener.onLike(post)
            }

            share.setOnClickListener {
                listener.onShare(post)
            }

            videoPlayButton.setOnClickListener{
                listener.onVideoLink(post)
            }

//            videoImage.setOnClickListener {
//                listener.onVideoLink(post)
//            }

            rootXmlElement.setOnClickListener {
                listener.onOpenPost(post)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
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
            Glide.with(avatar)
                .load("$BASE_URL/avatars/${post.authorAvatar}")
                .timeout(30_000)
                .circleCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .into(avatar)

            //attachmentImage.isVisible = post.attachment != null
            attachmentImage.visibility = if (post.attachment == null) View.GONE else View.VISIBLE
            Glide.with(attachmentImage)
                .load("$BASE_URL/images/${post.attachment?.url}")
                .timeout(30_000)
                .placeholder(R.mipmap.ic_launc



        }
    }
}
