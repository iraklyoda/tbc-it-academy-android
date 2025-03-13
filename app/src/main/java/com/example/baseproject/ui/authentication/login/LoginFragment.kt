package com.example.baseproject.ui.authentication.login
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.baseproject.databinding.FragmentLoginBinding
import com.example.baseproject.ui.BaseFragment
import com.example.baseproject.ui.utils.AuthFieldErrorMapper
import com.example.baseproject.ui.utils.collect
import com.example.baseproject.ui.utils.collectLatest
import com.example.baseproject.utils.makeVisibilityToggle
import com.example.baseproject.utils.onTextChanged
import com.example.baseproject.utils.showErrorToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun start() {
        setUpInputEvents()
        setPasswordVisibilityToggle()
    }

    override fun listeners() {
        registerFragmentListener()
        loginBtnListener()
        registerBtnListener()
    }

    override fun observers() {
        observeUiState()
        observeLoginEvents()
    }

    private fun observeUiState() {
        collectLatest(flow = loginViewModel.uiState) { state ->
            binding.apply {
                pbLogin.visibility = if (state.isLoading) View.VISIBLE else View.GONE

                etEmail.error =
                    AuthFieldErrorMapper.mapToString(state.emailError)?.let { getString(it) }

                etPassword.error =
                    AuthFieldErrorMapper.mapToString(state.passwordError)?.let { getString(it) }

                btnLogin.isEnabled = state.isLoginBtnEnabled
            }
        }
    }

    private fun observeLoginEvents() {
        collect(flow = loginViewModel.loginEvents) { event ->
            when (event) {
                is LoginEvent.LoginSuccess -> navigateToHome()
                is LoginEvent.LoginError ->
                    event.message?.let { requireContext().showErrorToast(it) }
            }
        }
    }

    private fun setUpInputEvents() {
        binding.apply {
            etEmail.onTextChanged { email ->
                loginViewModel.handleEvent(LoginUiEvents.EmailChanged(email))
            }
            etPassword.onTextChanged { password ->
                loginViewModel.handleEvent(LoginUiEvents.PasswordChanged(password))
            }
            cbRemember.setOnCheckedChangeListener { _, isChecked ->
                loginViewModel.handleEvent(LoginUiEvents.RememberMeChanged(isChecked))
            }
        }
    }

    private fun loginBtnListener() {
        binding.btnLogin.setOnClickListener {
            loginViewModel.handleEvent(LoginUiEvents.Submit)
        }
    }

    private fun registerBtnListener() {
        binding.btnRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
    }


    private fun navigateToHome() {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController().navigate(action)
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