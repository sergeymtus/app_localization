package ru.netology.nmedia

import ru.netology.nmedia.Post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.launch
import ru.netology.nmedia.databinding.ActivityMainBinding
import androidx.activity.viewModels
import ru.netology.nmedia.data.AndroidUtils
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.ui.NewPostResultContract
import ru.netology.nmedia.PostViewModel
import android.net.Uri


class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()


    private val newPostLauncher = registerForActivityResult(NewPostResultContract()){
        val result = it ?: return@registerForActivityResult
        viewModel.changeContent(result)
        viewModel.save()
    }

    private val editPostLauncher = registerForActivityResult(EditPostResultContract()){
        val result = it ?: return@registerForActivityResult
//        viewModel.changeContent(result)
//        viewModel.save()
        val arrTemp = result.split("<??!!!??>")

        if (arrTemp.size != 2) return@registerForActivityResult

        viewModel.editById(arrTemp[0].toLong(), arrTemp[1])




    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




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

            object : PostInteractionListener{
                override fun onEdit(post: Post) {
                    //viewModel.edit(post)
                    editPostLauncher.launch(post)
                }

                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onShare(post: Post) {
                    viewModel.shareById(post.id)

                    val intent = Intent(Intent.ACTION_SEND).apply {
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }

                    startActivity(intent)

//                val chooserIntent = Intent.createChooser(intent, null)
//
//                startActivity(chooserIntent)
                }

                override fun onVideoLink(post: Post) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                    startActivity(intent)
                }

            }
//            onLikeListener = { viewModel.likeById(it.id) },
//            onShareListener = { viewModel.shareById(it.id) },
//            onRemoveListener = { viewModel.removeById(it.id) }
        )

//        binding.save.setOnClickListener {
//            with(binding.content) {
//                if (text.isNullOrBlank()) {
//                    Toast.makeText(
//                        context,
//                        context.getString(R.string.empty_text_error),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@setOnClickListener
//                }
//
//                viewModel.changeContent(text.toString().trim())
//                viewModel.save()
//
//                clearFocus()
//                setText("")
//                AndroidUtils.hideKeyboard(this)
//
//
//            }
//        }


//        binding.list.adapter = adapter
//        viewModel.edited.observe(this){
//            if (it.id == 0L){
//                return@observe
//            }
//            with(binding.content){
//                setText(it.content)
//                requestFocus()
//
//
//            }
//
//        }


        binding.list.adapter = adapter
        viewModel.data.observe(this){ posts ->
            //adapter.List = posts
            adapter.submitList(posts)
        }


        binding.buttonOk.setOnClickListener{
            newPostLauncher.launch()
        }



        //отмена редактирования
//        viewModel.edited.observe(this) { post ->
//            if (post.id == 0L) {
//                return@observe
//            }
//
//            with(binding.content) {
//                requestFocus()
//                setText(post.content)
//                binding.group.visibility = View.VISIBLE
//            }
//        }

//        viewModel.edited.observe(this) { post ->
//            binding.editCancel.setOnClickListener {
//                with(binding.content) {
//
//                    clearFocus()
//                    setText("")
//
//
//                    AndroidUtils.hideKeyboard(this)
//                    binding.group.visibility = View.GONE
//                    viewModel.cancelEdit()
//
//                }
//
//            }
//
//
//       }



//        binding.editCancel.setOnClickListener {
//                with(binding.content) {
//
//                    clearFocus()
//                    setText("")
//
//
//                    AndroidUtils.hideKeyboard(this)
//                    binding.group.visibility = View.GONE
//
//                }
//            }

        //конец отмены редактирования


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
