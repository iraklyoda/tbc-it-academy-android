package com.example.baseproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import com.example.baseproject.databinding.ActivityMainBinding
import com.example.baseproject.domain.preferences.AppPreferenceKeys
import com.example.baseproject.domain.use_case.preferences.ReadPreferenceValueUseCase
import com.example.baseproject.ui.authentication.login.LoginFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var readPreferenceValueUseCase: ReadPreferenceValueUseCase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkToken()
    }

    private fun checkToken() {
        lifecycleScope.launch {
            val token: String? = readPreferenceValueUseCase(key = AppPreferenceKeys.TOKEN_KEY, defaultValue = "")?.firstOrNull()

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

