package org.wahid.movienight.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class Colors(
    val component: Color,
    val background: Color,
    val onBackground: Color,
    val bottomSheetColor: Color,
    val bottomIconColor: Color,
    val bottomIconActiveColor: Color,
    val text: Color,
    val subText: Color
)
val LOCAL_COLORS = staticCompositionLocalOf {
    Colors(
        component = Color.Unspecified,
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        bottomSheetColor = Color.Unspecified,
        bottomIconColor = Color.Unspecified,
        bottomIconActiveColor = Color.Unspecified,
        text = Color.Unspecified,
        subText = Color.Unspecified
    )
}