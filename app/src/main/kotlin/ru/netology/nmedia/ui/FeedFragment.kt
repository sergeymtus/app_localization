package ru.netology.nmedia.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.R
//import ru.netology.databinding.ActivityMainBinding
import ru.netology.databinding.CardPostBinding
import ru.netology.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    companion object {
        var Bundle.textArg: String?
            get() = getString("textArg")
            set(value) = putString("textArg", value)
        var Bundle.postIdArg: Long
            get() = getLong("postIdArg")
            set(value) = putLong("postIdArg", value)
    }

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

//    private val newPostLauncher = registerForActivityResult(NewPostResultContract()) {
//        val result = it ?: return@registerForActivityResult
//        viewModel.changeContent(result)
//        viewModel.save()
//    }
//
//    private val editPostLauncher = registerForActivityResult(EditPostResultCotract()) {
//        val result = it ?: return@registerForActivityResult
//
//        val arrTemp = result.split("<??!!!??>")
//
//        if (arrTemp.size != 2) return@registerForActivityResult
//
//        viewModel.editById(arrTemp[0].toLong(), arrTemp[1])
//        //viewModel.save()
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                //viewModel.edit(post)
                //editPostLauncher.launch(post)
                findNavController().navigate(R.id.action_feedFragment_to_editPostFragment,
                    Bundle().apply {
                        textArg = post.content
                        postIdArg = post.id
                    })
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

            }

            override fun onVideoLink(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(intent)
            }

            override fun onOpenPost(post: Post) {
                findNavController().navigate(R.id.action_feedFragment_to_postCardFragment,
                    Bundle().apply {
                        postIdArg = post.id
                    })
            }
        })


        binding.buttonAdd.setOnClickListener {
            //newPostLauncher.launch()
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)

        }


        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            //adapter.list = posts}
            adapter.submitList(posts)
        }


        return binding.root
    }
}

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        //Пример работы с Preferences+
////        val prefs = getPreferences(Context.MODE_PRIVATE)
////        prefs.edit().apply {
////            putString("key", "str test")
////            apply()
////        }
////
////        val stringInPrefs = prefs.getString("key3", null)
//        //Пример работы с Preferences-
//
//        //Пример работы с Preferences с Постами+
////        val prefs = getSharedPreferences("posts", Context.MODE_PRIVATE)
////        prefs.edit().apply {
////            putString("key", "str test")
////            apply()
////        }
////
////        val stringInPrefs = prefs.getString("key", null)
////        println(stringInPrefs)
//        //Пример работы с Preferences с Постами-
//
//
//
////        Так делается без binding+
////
////        setContentView(R.layout.activity_main)
////
////        findViewById<ImageButton>(R.id.likesImage).setOnClickListener{//println("Clicked!")
////            if (it !is ImageButton) {
////                return@setOnClickListener
////            }
////            it.setImageResource(R.drawable.ic_like_24)}
////        Так делается без binding-
////
////        Так делается c binding+
////        Чтобы включить биндинг надо в build.gradle в разделе андроид после
////        defaultConfig перед buildTypes написать: buildFeatures.viewBinding = true
//        /*     val binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val viewModelLazy: PostViewModel by viewModels()
//        viewModelLazy.data.observe(this) { post ->
//
//            with(binding) {
//                author.text = post.author
//                published.text = post.published
//                content.text = post.content
//                likesText.text = stringNumberWithKInsteadOf1000(post.likes)
//                sharesText.text = stringNumberWithKInsteadOf1000(post.shares)
//
//                likesImage.setImageResource(
//                    if (post.likedByMe) R.drawable.ic_like_24 else R.drawable.ic_like_border_24
//                )
//
//            }
//
//            binding.likesImage.setOnClickListener{
//                viewModelLazy.onClickLike()
//            }
//
//            binding.sharesImage.setOnClickListener{
//                viewModelLazy.onClickShare()
//            }
//
//        }
//
//
//        //22.05.2022 Сделал ветку mvvm
//
//
//        //Так делается с binding-
//    }*/
//
//
//        val binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//
////        viewModel.data.observe(this) { posts ->
////            posts.map { post ->
////                CardPostBinding.inflate(layoutInflater, binding.container, true).apply {
////                    author.text = post.author
////                    published.text = post.published
////                    content.text = post.content
////                    likesImage.setImageResource(
////                        if (post.likedByMe) R.drawable.ic_like_24 else R.drawable.ic_like_border_24
////                    )
////                    likesImage.setOnClickListener {
////                        viewModel.likeById(post.id)
////                    }
////                }.root
////
////            }
////
////        }
////        val adapter = PostsAdapter(
////            { viewModel.likeById(it.id) },
////            { viewModel.shareById(it.id) },
////            { viewModel.removeById(it.id) }
////        )
//
//        val adapter = PostsAdapter(object : OnInteractionListener {
//            override fun onEdit(post: Post) {
//                //viewModel.edit(post)
//                editPostLauncher.launch(post)
//            }
//
//            override fun onLike(post: Post) {
//                viewModel.likeById(post.id)
//            }
//
//            override fun onRemove(post: Post) {
//                viewModel.removeById(post.id)
//            }
//
//            override fun onShare(post: Post){
//                viewModel.shareById(post.id)
//
//                val intent = Intent(Intent.ACTION_SEND).apply {
//                    putExtra(Intent.EXTRA_TEXT, post.content)
//                    type = "text/plain"
//                }
//
//                startActivity(intent)
//
////                val chooserIntent = Intent.createChooser(intent, null)
////
////                startActivity(chooserIntent)
//            }
//
//            override fun onVideoLink(post: Post) {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
//                    startActivity(intent)
//            }
//        })
//
////        binding.save.setOnClickListener {
////            with(binding.content) {
////                if (text.isNullOrBlank()) {
////                    Toast.makeText(
////                        context,
////                        context.getString(R.string.empty_text_error),
////                        Toast.LENGTH_SHORT
////                    ).show()
////                    return@setOnClickListener
////                }
////
////                viewModel.changeContent(text.toString().trim())
////                viewModel.save()
////
////                clearFocus()
////                setText("")
////
////                AndroidUtils.hideKeyboard(this)
////                binding.group.visibility = View.GONE
////            }
////        }
//
//
//        binding.buttonAdd.setOnClickListener{
//            newPostLauncher.launch()
//        }
//
//
//        binding.list.adapter = adapter
//        viewModel.data.observe(this) { posts ->
//            //adapter.list = posts}
//            adapter.submitList(posts)
//        }
//
////        viewModel.edited.observe(this) { post ->
////            if (post.id == 0L) {
////                return@observe
////            }
////
////            with(binding.content) {
////                requestFocus()
////                setText(post.content)
////                binding.group.visibility = View.VISIBLE
////            }
////        }
//
////        binding.editCancel.setOnClickListener {
////            with(binding.content) {
////
////                clearFocus()
////                setText("")
////
////                AndroidUtils.hideKeyboard(this)
////                binding.group.visibility = View.GONE
////            }
////        }
//
//
//    }
//}