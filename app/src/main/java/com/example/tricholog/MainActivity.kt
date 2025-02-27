package com.example.tricholog

import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.tricholog.databinding.ActivityMainBinding
import com.example.tricholog.domain.auth.AuthManager
import com.example.tricholog.ui.landing.WelcomeFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @Inject
    lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        navHostFragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            if (authManager.isUserLoggedIn() && destination.id != R.id.dashboardFragment) {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        val action = WelcomeFragmentDirections.actionWelcomeFragmentToDashboardFragment()
        navController.navigate(action)
    }
}
