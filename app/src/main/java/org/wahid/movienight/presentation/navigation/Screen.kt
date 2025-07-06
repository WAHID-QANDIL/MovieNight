package org.wahid.movienight.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Splash: Screen()

    @Serializable
    data object Home: Screen()

    @Serializable
    data object Details: Screen()


    @Serializable
    data object Search: Screen()

}