
package ru.netology.nmedia

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.FeedFragment.Companion.postIdArg
import ru.netology.nmedia.FeedFragment.Companion.textArg
import ru.netology.nmedia.databinding.FragmentEditPostBinding
import android.widget.EditText



class EditPostFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditPostBinding.inflate(inflater, container, false)


//        val postId = intent.getLongExtra("postId", 0)
//
//        if (postId == 0L)
//            finish()
//
//
//        binding.text.text.append(intent.getStringExtra("text"))

        binding.text.text.append(arguments?.textArg as String)


        with(binding) {
            buttonOk.setOnClickListener {
                if (text.text.isNullOrBlank()) {
//                    Toast.makeText(
//                        this@EditPostFragment,
//                        getString(R.string.empty_text_error),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    setResult(Activity.RESULT_CANCELED)
//                    return@setOnClickListener
                    findNavController().navigateUp()

                } else {

//                    intent.putExtra("postId", postId)
//                    intent.putExtra(Intent.EXTRA_TEXT, text.text.toString())
//
//                    setResult(
//                        Activity.RESULT_OK,
////                        Intent().putExtra(Intent.EXTRA_TEXT, text.text.toString())
////                    )
//                        intent)
                    viewModel.editById(arguments?.postIdArg ?: 0, text.text.toString())

                }

                    findNavController().navigateUp()
                }




                return binding.root
            }
        }
}
