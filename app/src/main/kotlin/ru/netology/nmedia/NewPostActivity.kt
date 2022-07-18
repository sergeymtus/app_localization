package ru.netology.nmedia

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ru.netology.nmedia.databinding.NewPostActivityBinding


class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = NewPostActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with (binding) {
            buttonOk.setOnClickListener{
                if (text.text.isNullOrBlank()) {
                    Toast.makeText(
                        this@NewPostActivity,
                        getString(R.string.empty_text_error),
                        Toast.LENGTH_SHORT
                    ).show()
                    setResult(Activity.RESULT_CANCELED)
                    return@setOnClickListener
                } else {

                    setResult(Activity.RESULT_OK,
                        Intent().putExtra(Intent.EXTRA_TEXT, text.text.toString())
                    )
                }

                finish()
            }
        }
    }
}