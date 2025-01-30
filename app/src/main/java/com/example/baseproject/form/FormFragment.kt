package com.example.baseproject.form

import android.util.Log.d
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.User
import com.example.baseproject.databinding.FragmentFormBinding
import com.example.baseproject.utils.checkEmpty
import com.example.baseproject.utils.getString
import com.example.baseproject.utils.isEmail
import com.example.baseproject.utils.shake
import kotlinx.coroutines.launch

class FormFragment : BaseFragment<FragmentFormBinding>(FragmentFormBinding::inflate) {
    private val formViewModel: FormViewModel by viewModels()

    private var userExists: Boolean = false


    override fun start() {
        viewLifecycleOwner.lifecycleScope.launch {
            userExists = formViewModel.getUser() != null
            d("Robot", formViewModel.getUser()?.lastName ?: "Nope")
        }
    }

    override fun listeners() {
        onSave()
        showUser()
    }

    private fun onSave() {
        binding.btnSave.setOnClickListener {
            if (validateForm()) {
                val firstUserString = getString(R.string.you_are_saved_successfully)
                val updateUserString = getString(R.string.you_have_been_recreated_successfully)
                with(binding) {
                    val user = User(
                        firstName = etFirstName.getString(),
                        lastName = etLastName.getString(),
                        emailAddress = etEmail.getString()
                    )
                    formViewModel.saveUser(user)
                }
                Toast.makeText(
                    requireContext(),
                    if (!userExists) firstUserString else updateUserString, Toast.LENGTH_SHORT
                ).show()
                userExists = true
            }
        }
    }

    private fun showUser() {
        binding.btnRead.setOnClickListener {
            lifecycleScope.launch {
                if (!userExists) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.you_don_t_even_exist_as_of_yet), Toast.LENGTH_SHORT
                    ).show()
                } else {
                    UserFragment().show(parentFragmentManager, UserFragment().tag)
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        with(binding) {
            if (etFirstName.checkEmpty()) {
                etFirstName.error = getString(R.string.required_field)
                etFirstName.shake()
                return false
            }

            if (etLastName.checkEmpty()) {
                etLastName.error = getString(R.string.required_field)
                etLastName.shake()
                return false
            }

            if (etEmail.checkEmpty()) {
                etEmail.error = getString(R.string.required_field)
                etEmail.shake()
                return false
            } else if (!etEmail.isEmail()) {
                etEmail.error = getString(R.string.please_enter_proper_email)
                etEmail.shake()
                return false
            }
        }
        return true
    }
}