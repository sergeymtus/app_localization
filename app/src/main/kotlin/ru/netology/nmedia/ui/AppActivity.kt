package ru.netology.nmedia.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import com.google.firebase.messaging.FirebaseMessaging
import ru.netology.nmedia.databinding.ActivityAppBinding
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
        checkGoogleApiAvailability()
    }

    private fun checkGoogleApiAvailability() {
        with(GoogleApiAvailability.getInstance()) {
            val code = isGooglePlayServicesAvailable(this@AppActivity)
            if (code == ConnectionResult.SUCCESS) {
                return@with
            }
            if (isUserResolvableError(code)) {
                getErrorDialog(this@AppActivity, code, 9000)?.show()
                return
            }
            Toast.makeText(this@AppActivity, "Google Api Unavailable", Toast.LENGTH_LONG).show()
        }
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            println(it)

        }

    }
}