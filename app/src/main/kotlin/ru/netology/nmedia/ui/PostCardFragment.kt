package ru.netology.nmedia.ui

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
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.ui.FeedFragment.Companion.postIdArg
import ru.netology.nmedia.ui.FeedFragment.Companion.textArg
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.ui.OnInteractionListener

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





//        viewModel.data.observe(viewLifecycleOwner) { posts ->
//            //adapter.list = posts}
//            val post = posts.first { it.id == postId }
////            binding.like.text = extraViewFunctions().stringNumberWithKInsteadOf1000(post2.likes)
////
//            //val post = viewModel.getPostById(postId)
//
            //val post = viewModel.getPostById(postId)

        //            with(binding) {
//                author.text = post.author
//                published.text = post.published
//                content.text = post.content
//                // likesText.text = extraViewFunctions().stringNumberWithKInsteadOf1000(post.likes)
//                like.text = extraViewFunctions().stringNumberWithKInsteadOf1000(post.likes)
//                //sharesText.text = extraViewFunctions().stringNumberWithKInsteadOf1000(post.shares)
//                share.text = extraViewFunctions().stringNumberWithKInsteadOf1000(post.shares)
////            like.setImageResource(
////                if (post.likedByMe) R.drawable.ic_like_24 else R.drawable.ic_like_border_24
////            )
//                like.isChecked = post.likedByMe
//
//                if (post.video.isNullOrBlank()) groupVideo.visibility =
//                    View.GONE else groupVideo.visibility = View.VISIBLE
//
//                like.setOnClickListener {
//                    viewModel.likeById(post.id)
//                    context.let { PushSender().sendPushLikeTest(it, post) }
//                }
//
//                share.setOnClickListener {
//                    viewModel.shareById(post.id)
//
//                    val intent = Intent(Intent.ACTION_SEND).apply {
//                        putExtra(Intent.EXTRA_TEXT, post.content)
//                        type = "text/plain"
//                    }
//
//                    startActivity(intent)
//                }
//
//                videoPlayButton.setOnClickListener {
//                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
//                    startActivity(intent)
//                }
//
//                videoImage.setOnClickListener {
//                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
//                    startActivity(intent)
//                }
//
//                menu.setOnClickListener {
//                    PopupMenu(it.context, it).apply {
//                        inflate(R.menu.options_post)
//                        setOnMenuItemClickListener { item ->
//                            when (item.itemId) {
//                                R.id.remove -> {
//                                    viewModel.removeById(post.id)
//                                    findNavController().navigateUp()
//                                    true
//                                }
//                                R.id.edit -> {
//                                    findNavController().navigate(R.id.action_postCardFragment_to_editPostFragment,
//                                        Bundle().apply {
//                                            textArg = post.content
//                                            postIdArg = post.id
//                                        })
//                                    true
//                                }
//                                else -> false
//                            }
//
//                        }
//                    }.show()
//                }
//
//
//            }
//        }

        return binding.root
    }

}