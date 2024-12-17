package com.example.userapp

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.userapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUp()
    }

    private fun setUp() {
        setValues()
        addUser()
        updateUser()
        deleteUser()
    }

    private fun setValues() {
        val activeUsers: String = "${getString(R.string.active_users)}: ${User.activeUsers}"
        val deletedUsers: String = "${getString(R.string.deleted_users)}: ${User.deletedUsers}"
        binding.textActiveUsers.text = activeUsers
        binding.textDeletedUsers.text = deletedUsers
    }

    private fun addUser() {
        binding.btnAddUser.setOnClickListener {
            val user: User? = validateUser()
            if (user != null) {
                val isSuccess: Boolean = User.addUser(context = this, user = user)
                setResult(isSuccess)
                setValues()
            }
        }
    }

    private fun updateUser() {
        binding.btnUpdateUser.setOnClickListener {
            val user: User? = validateUser()
            if (user != null) {
                val isSuccess: Boolean = User.updateUser(context = this, user = user)
                setResult(isSuccess)
            }
        }
    }

    private fun deleteUser() {
        binding.btnRemoveUser.setOnClickListener {
            val email: String = binding.inputEmail.text.toString()

            if (email.isEmpty()) {
                binding.inputEmail.error = getString(R.string.email_is_required)
                return@setOnClickListener
            }

            val isSuccess: Boolean = User.removeUser(context = this, email = email)
            if (isSuccess) clearValues()
            setResult(isSuccess)
            setValues()
        }
    }

    private fun setResult(isSuccess: Boolean) {
        if (isSuccess) {
            binding.textResult.text = getString(R.string.success)
            binding.textResult.setTextColor(getColor(R.color.hookers_green))
        } else {
            binding.textResult.text = getString(R.string.fail)
            binding.textResult.setTextColor(getColor(R.color.light_coral))
        }
    }

    private fun validateUser(): User? {
        val firstName: String = binding.inputFirstName.text.toString()
        val lastName: String = binding.inputLastName.text.toString()
        val ageValue: String = binding.inputAge.text.toString()
        val email: String = binding.inputEmail.text.toString()

        val age: Int = if (ageValue.isEmpty()) 0 else ageValue.toInt()

        var formValid: Boolean = true

        if (firstName.isEmpty()) {
            binding.inputFirstName.error = getString(R.string.first_name_is_required)
            formValid = false
        }

        if (lastName.isEmpty()) {
            binding.inputLastName.error = getString(R.string.last_name_is_required)
            formValid = false
        }

        if (email.isEmpty()) {
            binding.inputEmail.error = getString(R.string.email_is_required)
            formValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.inputEmail.error = getString(R.string.please_enter_proper_email)
            formValid = false
        }

        if (age < 1 || age > 140) {
            binding.inputAge.error = getString(R.string.please_enter_proper_age)
            formValid = false
        }

        if (formValid) {
            val user: User = User(firstName, lastName, age, email)
            return user
        }
        return null
    }

    fun clearValues() {
        binding.inputFirstName.text.clear()
        binding.inputEmail.text.clear()
        binding.inputLastName.text.clear()
        binding.inputAge.text.clear()
    }
}