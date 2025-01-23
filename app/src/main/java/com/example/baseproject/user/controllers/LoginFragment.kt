package com.example.baseproject.user.controllers

import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.data.AuthPreferencesRepository
import com.example.baseproject.databinding.FragmentLoginBinding
import com.example.baseproject.user.LoginViewModel
import com.example.baseproject.user.UserDto
import com.example.baseproject.utils.getString
import com.example.baseproject.utils.isEmail
import com.example.baseproject.utils.makeVisibilityToggle
import com.example.baseproject.utils.showErrorToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

//    val sessionRepository: SessionRepository by lazy {
//        SessionRepository(requireContext())
//    }

    val authPreferencesRepository: AuthPreferencesRepository by lazy {
        AuthPreferencesRepository(requireContext())
    }

    private val loginViewModel: LoginViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(authPreferencesRepository) as T
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewLifecycleOwner.lifecycleScope.launch {
            val token = authPreferencesRepository.getToken()

            if (!token.isNullOrEmpty()) {
                navigateToHome()
            }
        }
    }

    override fun listeners() {
        registerFragmentListener()
        passwordVisibilityToggle()
        login()
        observeLoadingData()
        navigateToRegister()
    }

    private fun registerFragmentListener() {
        setFragmentResultListener("credentials") { _, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")

            binding.etEmail.setText(email)
            binding.etPassword.setText(password)
        }
    }

    private fun login() {
        binding.apply {
            btnLogin.setOnClickListener {
                if (validateForm()) {
                    loginViewModel.loginUser(
                        UserDto(
                            email = etEmail.getString(),
                            password = etPassword.getString()
                        ),
                        rememberMe = cbRemember.isChecked,
                        onSuccess = {
                            withContext(Dispatchers.Main) {
                                navigateToHome()
                            }
                        },
                        onError = { error ->
                            withContext(Dispatchers.Main) {
                                requireContext().showErrorToast(error)
                            }
                        }
                    )
                }
            }
        }
    }

    private fun navigateToHome() {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()

        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.loginFragment, true)
            .build()

        findNavController().navigate(action, navOptions)
    }

    private fun navigateToRegister() {
        binding.btnRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
    }

    private fun validateForm(): Boolean {
        with(binding) {
            if (!etEmail.getString().isEmail())
                return requireContext().showErrorToast(getString(R.string.please_enter_proper_email))

            if (etPassword.getString().isEmpty())
                return requireContext().showErrorToast(getString(R.string.please_enter_proper_password))
        }

        return true
    }

    private fun observeLoadingData() {
        viewLifecycleOwner.lifecycleScope.launch {
            loginViewModel.isLoading.collect() { loading ->
                binding.apply {
                    if (loading) {
                        pb.visibility = View.VISIBLE
                        btnLogin.isEnabled = false
                    } else {
                        pb.visibility = View.GONE
                        btnLogin.isEnabled = true
                    }
                }
            }
        }
    }

    private fun passwordVisibilityToggle() {
        binding.btnVisibility.makeVisibilityToggle(editText = binding.etPassword)
    }
}