package com.example.photoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.photoapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        returnToMain()
        clickNext()
        signUp()
    }

    private fun returnToMain() {
        binding.btnBackArrow.setOnClickListener() {
            if (binding.layoutStepOne.visibility == View.VISIBLE) {
                finish()
            } else {
                binding.layoutStepTwo.visibility = View.GONE
                binding.layoutStepOne.visibility = View.VISIBLE
            }
        }
    }

    private fun clickNext() {
        binding.btnNext.setOnClickListener() {
            var formValid: Boolean = true

            val email: String = binding.inputEmail.text.toString().trim()
            val password: String = binding.inputPassword.text.toString().trim()

            // Email Validation
            if (email.isEmpty()) {
                binding.inputEmail.error = getString(R.string.email_is_required)
                formValid = false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.inputEmail.error = getString(R.string.please_enter_a_proper_email)
                formValid = false
            }

            // Password validation
            if (password.isEmpty()) {
                binding.inputPassword.error = getString(R.string.password_is_required)
                formValid = false
            } else if (password.length < 6) {
                binding.inputPassword.error =
                    getString(R.string.password_should_contain_more_than_6_characters)
                formValid = false
            }

            if (formValid) {
                binding.layoutStepOne.visibility = View.GONE
                binding.layoutStepTwo.visibility = View.VISIBLE
            }
        }
    }

    private fun signUp() {
        binding.btnSignUp.setOnClickListener {
            val email: String = binding.inputEmail.text.toString().trim()
            val password: String = binding.inputPassword.text.toString().trim()
            val username: String = binding.inputUsername.text.toString().trim()


            if (username.isEmpty()) {
                binding.inputUsername.error = getString(R.string.username_is_required)
                return@setOnClickListener
            }


            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        d("TAG", "createUserWithEmail:success")
                        Toast.makeText(
                            baseContext,
                            "user has been created.",
                            Toast.LENGTH_SHORT
                        ).show()
                        val user: FirebaseUser? = auth.currentUser
                        val profileUpdates = userProfileChangeRequest {
                            displayName = username
                        }
                        // Update display name and only then transfer to welcome page
                        user!!.updateProfile(profileUpdates)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    d("TAG", "Username has been applied.")
                                    val intent: Intent = Intent(this, WelcomeActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                    } else {
                        d("TAG", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "authentication failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

}