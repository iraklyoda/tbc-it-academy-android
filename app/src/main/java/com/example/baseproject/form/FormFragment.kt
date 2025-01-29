package com.example.baseproject.form

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentFormBinding
import com.example.baseproject.utils.checkEmpty
import com.example.baseproject.utils.isEmail
import com.example.baseproject.utils.shake

class FormFragment : BaseFragment<FragmentFormBinding>(FragmentFormBinding::inflate){
    val formViewModel: FormViewModel by viewModels()

    override fun start() {

    }

    override fun listeners() {
        onSave()
    }

    private fun onSave() {
        binding.btnSave.setOnClickListener {
            if (validateForm()) {
                Toast.makeText(requireContext(), "You are saved successfully", Toast.LENGTH_SHORT).show()
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