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
import com.example.tricholog.ui.landing.WelcomeFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get NavHostFragment from correct ID
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        // Check authentication after NavController is ready
        navHostFragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            if (auth.currentUser != null && destination.id != R.id.homeFragment) {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        val action = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment()
        navController.navigate(action)
    }
}
