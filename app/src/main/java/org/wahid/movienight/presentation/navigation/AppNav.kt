package org.wahid.movienight.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.wahid.movienight.R
import org.wahid.movienight.presentation.Screen


@Composable
fun AppNav(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.Splash
    ) {
        composable<Screen.Splash> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.popcorn),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clickable(
                        onClick = {
                            navHostController.navigate(Screen.Home)
                        }
                    )
                )

            }

        }
        composable<Screen.Home> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Home")
            }


        }


    }
}