package com.example.photoapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.photoapp.databinding.ActivityWelcomeBinding
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setWelcome()
        logout()
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser == null) {
            returnToHome()
        }
    }

    private fun setWelcome() {
        val welcomeText: String = "${getString(R.string.welcome)} ${auth.currentUser?.displayName}"
        binding.txtWelcome.text = welcomeText
    }

    private fun logout() {
        binding.btnSignOut.setOnClickListener {
            auth.signOut()
            if (auth.currentUser == null) {
                returnToHome()
            }
        }
    }

    private fun returnToHome() {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}