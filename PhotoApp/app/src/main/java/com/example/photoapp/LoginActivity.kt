package com.example.photoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.photoapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        returnToMain()
        login()
    }

    private fun returnToMain() {
        binding.btnBackArrow.setOnClickListener() {
            finish()
        }
    }

    private fun login() {
        binding.btnLogIn.setOnClickListener {
            val email: String = binding.inputEmail.text.toString().trim()
            val password: String = binding.inputPassword.text.toString().trim()

            var formValid: Boolean = true

            if (email.isEmpty()) {
                binding.inputEmail.error = getString(R.string.email_is_required)
                formValid = false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.inputEmail.error = getString(R.string.please_enter_a_proper_email)
                formValid = false
            }

            if (password.isEmpty()) {
                binding.inputPassword.error = getString(R.string.password_is_required)
                formValid = false
            }

            if (formValid) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            d("TAG", "signInWithEmail:success")
                            val user = auth.currentUser
                            val intent: Intent = Intent(this, WelcomeActivity::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            d("TAG", "signInWithEmail:failure", task.exception)

                            val message: String? = task.exception?.message

                            // Check for errors coming from firebase
                            if (message != null) {
                                if (message.contains("auth credential")) {
                                    binding.inputPassword.error =
                                        getString(R.string.email_or_password_is_incorrect)
                                } else {
                                    Toast.makeText(
                                        baseContext,
                                        task.exception?.message,
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }
                            }
                        }
                    }
            }
        }
    }
}