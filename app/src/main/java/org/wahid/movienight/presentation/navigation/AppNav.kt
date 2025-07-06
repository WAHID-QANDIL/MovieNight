package org.wahid.movienight.presentation.navigation

      import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import org.wahid.movienight.domain.model.Movie
import org.wahid.movienight.presentation.screen.details.DetailsScreen
import org.wahid.movienight.presentation.screen.details.DetailsViewModel
import org.wahid.movienight.presentation.screen.home.HomeScreen
import org.wahid.movienight.presentation.screen.home.HomeViewModel
      import org.wahid.movienight.presentation.screen.search.SearchScreen
      import org.wahid.movienight.presentation.screen.search.SearchViewModel

@RequiresApi(Build.VERSION_CODES.S)
      @Composable
      fun AppNav(navHostController: NavHostController) {

          NavHost(
              navController = navHostController,
              startDestination = Screen.Splash
          ) {
              composable<Screen.Splash>(
                  enterTransition = {
                      slideIntoContainer(
                          towards = AnimatedContentTransitionScope.SlideDirection.Start,
                          animationSpec = tween(300)
                      ) + fadeIn(animationSpec = tween(300))
                  },
                  exitTransition = {
                      slideOutOfContainer(
                          towards = AnimatedContentTransitionScope.SlideDirection.Start,
                          animationSpec = tween(300)
                      ) + fadeOut(animationSpec = tween(300))
                  },
                  popEnterTransition = {
                      slideIntoContainer(
                          towards = AnimatedContentTransitionScope.SlideDirection.End,
                          animationSpec = tween(300)
                      ) + fadeIn(animationSpec = tween(300))
                  },
                  popExitTransition = {
                      slideOutOfContainer(
                          towards = AnimatedContentTransitionScope.SlideDirection.End,
                          animationSpec = tween(300)
                      ) + fadeOut(animationSpec = tween(300))
                  }
              ) {
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

              composable<Screen.Home>(
                  enterTransition = {
                      slideIntoContainer(
                          towards = AnimatedContentTransitionScope.SlideDirection.Start,
                          animationSpec = tween(300)
                      ) + fadeIn(animationSpec = tween(300))
                  },
                  exitTransition = {
                      slideOutOfContainer(
                          towards = AnimatedContentTransitionScope.SlideDirection.Start,
                          animationSpec = tween(300)
                      ) + fadeOut(animationSpec = tween(300))
                  },
                  popEnterTransition = {
                      slideIntoContainer(
                          towards = AnimatedContentTransitionScope.SlideDirection.End,
                          animationSpec = tween(300)
                      ) + fadeIn(animationSpec = tween(300))
                  },
                  popExitTransition = {
                      slideOutOfContainer(
                          towards = AnimatedContentTransitionScope.SlideDirection.End,
                          animationSpec = tween(300)
                      ) + fadeOut(animationSpec = tween(300))
                  }
              ) {
                  val vm = hiltViewModel<HomeViewModel>()
                  HomeScreen(
                      viewModel = vm,
                      onClickMovie = {movie, source ->
                          navHostController.currentBackStackEntry?.savedStateHandle?.set("movie", movie)
                          navHostController.navigate(Screen.Details)
                      },
                      onClickSearch = {
                          navHostController.navigate(Screen.Search)
                      },
                  )
              }

              composable<Screen.Details>(
                  enterTransition = {
                      slideIntoContainer(
                          towards = AnimatedContentTransitionScope.SlideDirection.Start,
                          animationSpec = tween(300)
                      ) + fadeIn(animationSpec = tween(300))
                  },
                  exitTransition = {
                      slideOutOfContainer(
                          towards = AnimatedContentTransitionScope.SlideDirection.Start,
                          animationSpec = tween(300)
                      ) + fadeOut(animationSpec = tween(300))
                  },
                  popEnterTransition = {
                      slideIntoContainer(
                          towards = AnimatedContentTransitionScope.SlideDirection.End,
                          animationSpec = tween(300)
                      ) + fadeIn(animationSpec = tween(300))
                  },
                  popExitTransition = {
                      slideOutOfContainer(
                          towards = AnimatedContentTransitionScope.SlideDirection.End,
                          animationSpec = tween(300)
                      ) + fadeOut(animationSpec = tween(300))
                  }
              ) { backStackEntry ->
                  val viewModel = hiltViewModel<DetailsViewModel>()
                  val movie = navHostController.previousBackStackEntry?.savedStateHandle?.get<Movie>("movie") ?: return@composable

                  DetailsScreen(
                      viewModel = viewModel,
                      movie = movie,
                      onBackClick = { navHostController }
                  )
              }

              composable<Screen.Search>(
                  enterTransition = {
                      slideIntoContainer(
                          towards = AnimatedContentTransitionScope.SlideDirection.Start,
                          animationSpec = tween(300)
                      ) + fadeIn(animationSpec = tween(300))
                  },
                  exitTransition = {
                      slideOutOfContainer(
                          towards = AnimatedContentTransitionScope.SlideDirection.Start,
                          animationSpec = tween(300)
                      ) + fadeOut(animationSpec = tween(300))
                  },
                  popEnterTransition = {
                      slideIntoContainer(
                          towards = AnimatedContentTransitionScope.SlideDirection.End,
                          animationSpec = tween(300)
                      ) + fadeIn(animationSpec = tween(300))
                  },
                  popExitTransition = {
                      slideOutOfContainer(
                          towards = AnimatedContentTransitionScope.SlideDirection.End,
                          animationSpec = tween(300)
                      ) + fadeOut(animationSpec = tween(300))
                  }
              ) {
                  val viewModel = hiltViewModel<SearchViewModel>()

                  SearchScreen(
                      viewModel = viewModel,
                      onBackClick = { navHostController.popBackStack() },
                      onMovieClick = { movie ->
                          navHostController.currentBackStackEntry?.savedStateHandle?.set("movie", movie)
                          navHostController.navigate(Screen.Details)
                      }
                  )
              }
          }
      }