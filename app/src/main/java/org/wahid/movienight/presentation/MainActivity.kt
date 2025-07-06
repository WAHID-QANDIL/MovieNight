package org.wahid.movienight.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.wahid.movienight.presentation.navigation.AppNav
import org.wahid.movienight.ui.theme.MovieNightTheme

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color(0xFF343838).toArgb()),
            navigationBarStyle = SystemBarStyle.dark(Color(0xFF343838).toArgb())
        )

        setContent {
            MovieNightTheme {
                val navHostController = rememberNavController()
                Surface(
                    color = MovieNightTheme.colors.background
                ) {
                    AppNav(navHostController = navHostController)
                }
            }
        }
    }


}