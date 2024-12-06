package com.example.registrationformapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.util.Patterns
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val formLayout: LinearLayout = findViewById<LinearLayout>(R.id.form)
        val profileLayout: LinearLayout = findViewById<LinearLayout>(R.id.profile)

        // Form Views

        val inputEmail: EditText = findViewById<EditText>(R.id.email_input)
        val inputUsername: EditText = findViewById<EditText>(R.id.username_input)
        val inputFirstName: EditText = findViewById<EditText>(R.id.first_name_input)
        val inputLastName: EditText = findViewById<EditText>(R.id.last_name_input)
        val inputAge: EditText = findViewById<EditText>(R.id.age_input)

        val btnSave: Button = findViewById<Button>(R.id.btn_save)
        val btnClear: Button = findViewById<Button>(R.id.btn_clear)

        // Profile Page Views

        val email: TextView = findViewById<EditText>(R.id.email)
        val username: TextView = findViewById<EditText>(R.id.username)
        val name: TextView = findViewById<EditText>(R.id.name)
        val age: TextView = findViewById<EditText>(R.id.age)

        val btnAgain: Button = findViewById<Button>(R.id.btn_again)

        fun clearInputs() {
            inputEmail.text.clear()
            inputUsername.text.clear()
            inputFirstName.text.clear()
            inputLastName.text.clear()
            inputAge.text.clear()
        }

        btnSave.setOnClickListener {

            val emailValue: String = inputEmail.text.toString().trim()
            val usernameValue: String = inputUsername.text.toString().trim()
            val firstNameValue: String = inputFirstName.text.toString().trim()
            val lastNameValue: String = inputLastName.text.toString().trim()
            val ageValue: String = inputAge.text.toString().trim()

            var formValid: Boolean = true

            // Email validation

            if (emailValue.isEmpty()) {
                inputEmail.error = getString(R.string.email_required)
                formValid = false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
                inputEmail.error = getString(R.string.please_enter_proper_email)
                formValid = false
            }


            // Username Validation

            if (usernameValue.isEmpty()) {
                inputUsername.error = getString(R.string.username_is_required)
                formValid = false
            } else if(usernameValue.length < 10) {
                inputUsername.error =
                    getString(R.string.username_should_contain_more_than_10_characters)
                formValid = false
            }

            // Name validation

            if (firstNameValue.isEmpty()) {
                inputFirstName.error = getString(R.string.first_name_is_required)
                formValid = false
            }

            if (lastNameValue.isEmpty()) {
                inputLastName.error = getString(R.string.last_name_is_required)
                formValid = false
            }

            // Age Validation

            if (ageValue.isEmpty()) {
                inputAge.error = getString(R.string.age_is_required)
                formValid = false
            } else if (ageValue.toInt() < 0) {
                inputAge.error = getString(R.string.age_should_be_a_whole_number)
                formValid = false
            }

            if (formValid) {
                formLayout.visibility = View.GONE
                clearInputs()

                email.text = emailValue
                username.text = usernameValue
                name.text = "$firstNameValue $lastNameValue"
                age.text = ageValue

                profileLayout.visibility = View.VISIBLE
            }

            btnAgain.setOnClickListener() {
                profileLayout.visibility = View.GONE
                formLayout.visibility = View.VISIBLE
            }
        }

        // Clear

        btnClear.setOnLongClickListener {
            clearInputs()
            true
        }
    }
}