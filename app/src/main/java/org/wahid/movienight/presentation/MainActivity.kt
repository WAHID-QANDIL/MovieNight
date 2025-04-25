package org.wahid.movienight.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.wahid.movienight.presentation.navigation.AppNav
import org.wahid.movienight.ui.theme.MovieNightTheme


@AndroidEntryPoint
class MainActivity()
    : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieNightTheme {
                val navHostController = rememberNavController()
                AppNav(navHostController = navHostController)
            }
        }
    }
}