package ru.netology.nmedia

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.data.AndroidUtils
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import android.widget.EditText
import android.widget.TextView


//import ru.netology.nmedia.databinding.NewPostActivityBinding


class NewPostFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentNewPostBinding.inflate(inflater, container, false)

        with(binding) {
            buttonOk.setOnClickListener {
//                viewModel.changeContent(text.text.toString())
//                viewModel.save()
//
//                AndroidUtils.hideKeyboard(requireView())
//                findNavController().navigateUp()
                buttonOkListener(it, text)


//                if (text.text.isNullOrBlank()) {
//                    Toast.makeText(
//                        this@NewPostFragment,
//                        getString(R.string.empty_text_error),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    setResult(Activity.RESULT_CANCELED)
//                    return@setOnClickListener
//                } else {
//
//                    setResult(Activity.RESULT_OK,
//                        Intent().putExtra(Intent.EXTRA_TEXT, text.text.toString())
//                    )
//                }
//
//                finish()

            }

            return binding.root

        }



    }
    private fun buttonOkListener(view: View, editText: EditText) {
        viewModel.changeContent(editText.text.toString())
        viewModel.save()

        AndroidUtils.hideKeyboard(requireView())
        val navigateUp = findNavController().navigateUp()

    }

}