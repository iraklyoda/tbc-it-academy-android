package com.iraklyoda.userssocialapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.iraklyoda.userssocialapp.presentation.navigation.AppNavGraph
import com.iraklyoda.userssocialapp.presentation.theme.UsersSocialAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           UsersSocialAppTheme {
               val navController = rememberNavController()
               AppNavGraph(navController = navController)
           }
        }
    }
}

