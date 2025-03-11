package com.example.baseproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import com.example.baseproject.data.local.AuthPreferencesRepository
import com.example.baseproject.databinding.ActivityMainBinding
import com.example.baseproject.ui.authentication.login.LoginFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var authPreferencesRepository: AuthPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkToken()
    }

    private fun checkToken() {
        lifecycleScope.launch {
            val token: String? = authPreferencesRepository.getToken()

            if (!token.isNullOrEmpty()) {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController(binding.fragmentContainerView).navigate(action)
    }
}

