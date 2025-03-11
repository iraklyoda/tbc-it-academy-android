package com.example.baseproject.ui.authentication.login

import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.baseproject.ui.BaseFragment
import com.example.baseproject.domain.common.Resource
import com.example.baseproject.databinding.FragmentLoginBinding
import com.example.baseproject.ui.utils.FieldErrorMapper
import com.example.baseproject.ui.utils.collect
import com.example.baseproject.ui.utils.collectLatest
import com.example.baseproject.utils.makeVisibilityToggle
import com.example.baseproject.utils.onTextChanged
import com.example.baseproject.utils.setLoaderState
import com.example.baseproject.utils.showErrorToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun start() {
        setPasswordVisibilityToggle()
        setUpInputEvents()
    }

    override fun listeners() {
        registerFragmentListener()
        loginBtnListener()
        registerBtnListener()
    }

    override fun observers() {
        observeLoginState()
        observeFormValidationErrors()
        observeValidationEvents()
    }

    private fun observeLoginState() {
        collectLatest(loginViewModel.loginStateFlow) { resource ->
            when (resource) {
                is Resource.Loading -> binding.pbLogin.setLoaderState(loading = true)

                is Resource.Success -> {
                    resource.data?.let {
                        binding.pbLogin.setLoaderState(
                            loading = false,
                        )
                        loginViewModel.saveAuthPreferences(token = it.token, email = it.email)
                        navigateToHome()
                    }
                }

                is Resource.Error -> {
                    binding.pbLogin.setLoaderState(loading = false)
                    requireContext().showErrorToast(resource.errorMessage)
                }
            }
        }
    }

    private fun observeFormValidationErrors() {
        collect(flow = loginViewModel.loginFormState) { state ->
            binding.apply {
                etEmail.error =
                    FieldErrorMapper.mapToString(state.emailError)?.let { getString(it) }
                etPassword.error =
                    FieldErrorMapper.mapToString(state.passwordError)?.let { getString(it) }
            }
        }
    }

    private fun observeValidationEvents() {
        collect(flow = loginViewModel.validationEvents) { event ->
            when (event) {
                is LoginViewModel.ValidationEvent.Success -> {
                    loginViewModel.login()
                }
            }
        }
    }

    private fun setUpInputEvents() {
        binding.apply {
            etEmail.onTextChanged { email ->
                loginViewModel.handleEvent(LoginFormEvent.EmailChanged(email))
            }
            etPassword.onTextChanged { password ->
                loginViewModel.handleEvent(LoginFormEvent.PasswordChanged(password))
            }
            cbRemember.setOnCheckedChangeListener { _, isChecked ->
                loginViewModel.handleEvent(LoginFormEvent.RememberMeChanged(isChecked))
            }
        }
    }

    private fun loginBtnListener() {
        binding.btnLogin.setOnClickListener {
            loginViewModel.handleEvent(LoginFormEvent.Submit(rememberMe = binding.cbRemember.isChecked))
        }
    }

    private fun navigateToHome() {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    private fun registerBtnListener() {
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

    private fun setPasswordVisibilityToggle() {
        binding.btnVisibility.makeVisibilityToggle(editText = binding.etPassword)
    }
}