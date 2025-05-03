package org.wahid.movienight.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.wahid.movienight.R
import org.wahid.movienight.presentation.screen.home.HomeScreen
import org.wahid.movienight.presentation.screen.home.HomeViewModel


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AppNav(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.Home
    ) {
        composable<Screen.Splash> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.popcorn),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.clickable(
                        onClick = {
                            navHostController.popBackStack()
                            navHostController.navigate(Screen.Home)
                        }
                    )
                )

            }

        }
        composable<Screen.Home> {
            val vm = hiltViewModel<HomeViewModel>()
            HomeScreen(
                viewModel = vm
            )
        }


    }
}