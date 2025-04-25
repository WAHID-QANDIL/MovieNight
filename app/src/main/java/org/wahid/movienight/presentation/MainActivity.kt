package org.wahid.movienight.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.wahid.movienight.presentation.navigation.AppNav
import org.wahid.movienight.ui.theme.MovieNightTheme

@AndroidEntryPoint
class MainActivity()
    : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color(0XFF67686D).toArgb()),
            navigationBarStyle = SystemBarStyle.dark(Color(0XFF67686D).toArgb())
        )
        setContent {
            MovieNightTheme {
                val navHostController = rememberNavController()
                AppNav(navHostController = navHostController)
            }
        }
    }


}