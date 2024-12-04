package com.example.registrationformapp

import android.app.appsearch.StorageInfo
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.util.Patterns
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputEmail: EditText = findViewById<EditText>(R.id.email_input)
        val inputUsername: EditText = findViewById<EditText>(R.id.username_input)
        val inputFirstName: EditText = findViewById<EditText>(R.id.first_name_input)
        val inputLastName: EditText = findViewById<EditText>(R.id.last_name_input)
        val inputAge: EditText = findViewById<EditText>(R.id.age_input)

        val btnSave: Button = findViewById<Button>(R.id.btn_save)
        val btnClear: Button = findViewById<Button>(R.id.btn_clear)

        btnSave.setOnClickListener {
            val email: String = inputEmail.text.toString().trim()
            val username: String = inputUsername.text.toString().trim()
            val firstName: String = inputFirstName.text.toString().trim()
            val lastName: String = inputLastName.text.toString().trim()
            val age: String = inputAge.text.toString().trim()

            // Email validation

            if (email.isEmpty()) {
                inputEmail.error = getString(R.string.email_required)
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                inputEmail.error = getString(R.string.please_enter_proper_email)
            }


            // Username Validation

            if (username.isEmpty()) {
                inputUsername.error = getString(R.string.username_is_required)
            } else if(username.length < 10) {
                inputUsername.error =
                    getString(R.string.username_should_contain_more_than_10_characters)
            }

            // Name validation

            if (firstName.isEmpty()) {
                inputFirstName.error = getString(R.string.first_name_is_required)
            }

            if (lastName.isEmpty()) {
                inputLastName.error = getString(R.string.last_name_is_required)
            }

            // Age Validation

            if (age.isEmpty()) {
                inputAge.error = getString(R.string.age_is_required)
            } else if (age.toInt() < 0) {
                inputAge.error = getString(R.string.age_should_be_a_whole_number)
            }
        }

        // Clear

        btnClear.setOnLongClickListener {
            inputEmail.text.clear()
            inputUsername.text.clear()
            inputFirstName.text.clear()
            inputLastName.text.clear()
            inputAge.text.clear()
            true
        }
    }
}