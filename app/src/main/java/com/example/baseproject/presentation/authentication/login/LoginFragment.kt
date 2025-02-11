package com.example.baseproject.presentation.authentication.login

import android.util.Log.d
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.common.Resource
import com.example.baseproject.data.local.AuthPreferencesRepository
import com.example.baseproject.data.remote.AuthRepository
import com.example.baseproject.databinding.FragmentLoginBinding
import com.example.baseproject.utils.getString
import com.example.baseproject.utils.isEmail
import com.example.baseproject.utils.makeVisibilityToggle
import com.example.baseproject.utils.setLoaderState
import com.example.baseproject.utils.showErrorToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val authRepository: AuthRepository by lazy {
        AuthRepository(
            authPreferencesRepository = AuthPreferencesRepository(requireContext().applicationContext)
        )
    }

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModel.Factory(authRepository)
    }

    override fun listeners() {
        observeLoginState()
        registerFragmentListener()
        passwordVisibilityToggle()
        login()
        navigateToRegister()
    }

    private fun observeLoginState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                loginViewModel.loginStateFlow.collectLatest { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                                binding.pbLogin.setLoaderState(
                                    loading = resource.loading,
                                    actionBtn = binding.btnLogin
                                )
                        }
                        is Resource.Success -> {
                            navigateToHome()
                        }
                        is Resource.Error -> {
                            d("LoginError", "$resource")
                            requireContext().showErrorToast(resource.errorMessage)
                        }
                    }
                }
            }
        }
    }

    private fun login() {
        binding.apply {
            btnLogin.setOnClickListener {
                if (validateForm()) {
                    loginViewModel.login(
                        email = etEmail.getString(),
                        password = etPassword.getString(),
                        rememberMe = cbRemember.isChecked
                    )
                }
            }
        }
    }

    private fun navigateToHome() {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    private fun navigateToRegister() {
        binding.btnRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
    }

    private fun registerFragmentListener() {
        setFragmentResultListener("credentials") { _, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")

            binding.etEmail.setText(email)
            binding.etPassword.setText(password)
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

    private fun passwordVisibilityToggle() {
        binding.btnVisibility.makeVisibilityToggle(editText = binding.etPassword)
    }
}