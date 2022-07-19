
package ru.netology.nmedia

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityEditPostBinding


class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val postId = intent.getLongExtra("postId", 0)

        if (postId == 0L)
            finish()


        binding.text.text.append(intent.getStringExtra("text"))




        with (binding) {
            buttonOk.setOnClickListener{
                if (text.text.isNullOrBlank()) {
                    Toast.makeText(
                        this@EditPostActivity,
                        getString(R.string.empty_text_error),
                        Toast.LENGTH_SHORT
                    ).show()
                    setResult(Activity.RESULT_CANCELED)
                    return@setOnClickListener
                } else {

                    intent.putExtra("postId", postId)
                    intent.putExtra(Intent.EXTRA_TEXT, text.text.toString())

                    setResult(
                        Activity.RESULT_OK,
//                        Intent().putExtra(Intent.EXTRA_TEXT, text.text.toString())
//                    )
                        intent)
                }

                finish()
            }
        }
    }
}