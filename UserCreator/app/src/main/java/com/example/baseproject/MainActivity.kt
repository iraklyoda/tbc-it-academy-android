package com.example.baseproject

import android.os.Bundle
import android.util.Log.d
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.baseproject.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nameInput: EditText = binding.inputFullName
        val emailInput: EditText = binding.inputEmail
        val addUserBtn: Button = binding.btnAddUser
        val usersInfoText: TextView = binding.txtUserCount


        val emailSearchInput: EditText = binding.inputEmailSearch
        val getUserBtn: Button = binding.btnGetUser
        val userNameText: TextView = binding.txtNameInfo
        val userEmailText: TextView = binding.txtEmailInfo


        addUserBtn.setOnClickListener {
            var formValid: Boolean = true
            val nameValue: String = nameInput.text.toString().trim()
            val emailValue: String = emailInput.text.toString().trim()

            // Full Name validation
            if (nameValue.isEmpty()) {
                nameInput.error = getString(R.string.name_is_required)
                formValid = false
            }

            // Email validation
            if (emailValue.isEmpty()) {
                emailInput.error = getString(R.string.email_is_required)
                formValid = false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
                emailInput.error = "Please enter proper email"
                formValid = false
            } else if (User.usersMap[emailValue] != null) {
                emailInput.error = "Email already exists. Try another"
                formValid = false
            }

            if (formValid) {
                val user = User(
                    nameInput.text.toString(),
                    emailInput.text.toString()
                )

                usersInfoText.text = "Users -> ${User.usersCount}"
            }
        }

        getUserBtn.setOnClickListener {
            var formValid: Boolean = true
            val emailSearchValue = emailSearchInput.text.toString().trim().lowercase()

            // Validation
            if (emailSearchValue.isEmpty()) {
                emailSearchInput.error = getString(R.string.email_is_required)
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailSearchValue).matches()) {
                emailSearchInput.error = "Please enter proper email"
                formValid = false
            } else if (!User.usersMap.containsKey(emailSearchValue)) {
                emailSearchInput.error = "User Not Found"
                formValid = false
            }

            if (formValid) {
                userNameText.text = emailSearchValue
                userEmailText.text = User.usersMap[emailSearchValue]
            }
        }


    }
}