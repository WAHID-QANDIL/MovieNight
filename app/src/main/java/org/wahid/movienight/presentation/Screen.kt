package org.wahid.movienight.presentation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Splash: Screen()

    @Serializable
    data object Home: Screen()

}