package org.wahid.movienight.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.runtime.staticCompositionLocalOf
import org.wahid.movienight.R

data class Icons(
    @DrawableRes val movie: Int,
    @DrawableRes val search: Int,
    @DrawableRes val clear: Int,
    @DrawableRes val arrowBack: Int
)

val LOCAL_ICONS = staticCompositionLocalOf {
    Icons(
        movie = R.drawable.movie,
        search = R.drawable.baseline_search_24,
        clear = R.drawable.clear,
        arrowBack = R.drawable.baseline_arrow_back_24
    )
}