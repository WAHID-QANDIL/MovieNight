package org.wahid.movienight.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.runtime.staticCompositionLocalOf
import org.wahid.movienight.R

data class Icons(
    @DrawableRes
    val movie: Int
)

val LOCAL_ICONS = staticCompositionLocalOf {
    Icons(
        movie = R.drawable.movie
    )
}