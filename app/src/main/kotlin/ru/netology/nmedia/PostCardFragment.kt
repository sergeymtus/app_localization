package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.FeedFragment.Companion.postIdArg
import ru.netology.nmedia.FeedFragment.Companion.textArg
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.databinding.FragmentNewPostBinding



class PostCardFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CardPostBinding.inflate(inflater, container, false)
        val postId = arguments?.postIdArg as Long

        if (postId == 0L) {
            findNavController().navigateUp()
            //return
        }

        viewModel.data.observe(viewLifecycleOwner) { posts ->

//            val post = viewModel.getPostById(postId)
            val post = posts.firstOrNull { it.id == postId }

            with(binding) {
            author.text = post?.author
            published.text = post?.published
            content.text = post?.content
            //           likes.text = countNumber(post.likesCount)
                if (post != null) {
                    like.text = countNumber(post.likesCount)
                }
//            shares.text = countNumber(post.shareCountValue)
                if (post != null) {
                    share.text = countNumber(post.shareCountValue)
                }
            views.text = post?.viewCountValue.toString()

                if (post != null) {
                    like.isChecked = post.likedByMe
                }
//            like.setImageResource(
//                if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
//            )


            like.setOnClickListener {
                if (post != null) {
                    viewModel.likeById(post.id)
                }
            }

            share.setOnClickListener {
                if (post != null) {
                    viewModel.shareById(post.id)
                }

                val intent = Intent(Intent.ACTION_SEND).apply {
                    putExtra(Intent.EXTRA_TEXT, post?.content)
                    type = "text/plain"
                }

                startActivity(intent)
            }


            if (post?.video.isNullOrBlank()) groupVideo.visibility =
                android.view.View.GONE else groupVideo.visibility = android.view.View.VISIBLE

            videoPlayButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post?.video))
                startActivity(intent)
            }

            videoImage.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post?.video))
                startActivity(intent)
            }

//            rootXmlElement.setOnClickListener {
//                listener.onOpenPost(post)
//            }


            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_menu)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                if (post != null) {
                                    viewModel.removeById(post.id)
                                }
                                findNavController().navigateUp()
                                true
                            }
                            R.id.edit -> {
                                findNavController().navigate(R.id.action_postCardFragment_to_editPostFragment,
                                    Bundle().apply {
                                        textArg = post?.content
                                        if (post != null) {
                                            postIdArg = post.id
                                        }
                                    })
                                true
                            }
                            else -> false
                        }

                    }
                }.show()
            }


            }
        }

        return binding.root
    }

}
