package ru.netology.nmedia.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ru.netology.R
import ru.netology.databinding.ActivityAppBinding
//import ru.netology.databinding.ActivityIntentHandlerBinding

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.action == Intent.ACTION_SEND){

            val text = intent.getStringExtra(Intent.EXTRA_TEXT)
            if (text.isNullOrBlank()){
                Snackbar.make(binding.root, getString(R.string.blank_message_text), Snackbar.LENGTH_SHORT)
                    .apply {
                        setAction(android.R.string.ok){
                            finish()
                        }
                    }
                    .show()
            }

            //binding.text.text = text
        }
    }
}