package org.wahid.movienight.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.wahid.movienight.R

// For more information about custom theming visit: https://developer.android.com/develop/ui/compose/designsystems/custom

@Composable
fun MovieNightTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    val colors = Colors(
        component = Color(0xff67686D),
        background = if (isDark) Color(0xFF1E1E1E) else Color(0XFFFFFFFF),
        onBackground = Color(0XFF0296E5),
        bottomSheetColor = if (isDark) Color(0xFF1E1E1E) else Color(0XFFFFFFFF),
        bottomIconColor = Color(0XFF67686D),
        bottomIconActiveColor = Color(0XFF0296E5),
        text = Color(0XFFFFFFFF),
        subText = Color(0XFF67686D),
        error = Color(0XFF67686D)

    )

    val typography = Typography(
        titleVeryLarge =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W700,
                fontSize = 18.sp,
                lineHeight = 21.09.sp
            ),
        titleLarge =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W700,
                fontSize = 16.sp,
                lineHeight = 18.75.sp
            ),
        titleMedium =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W700,
                fontSize = 14.sp,
                lineHeight = 16.41.sp
            ),
        titleSmall =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W700,
                fontSize = 12.sp,
                lineHeight = 14.06.sp
            ),
        bodyLarge =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                lineHeight = 18.75.sp
            ),
        bodyMedium =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                lineHeight = 16.41.sp
            ),
        bodySmall =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W400,
                fontSize = 12.sp,
                lineHeight = 14.06.sp
            )
    )

    val shapes =
        Shapes(
            large = RoundedCornerShape(24.dp),
            medium = RoundedCornerShape(16.dp),
            small = RoundedCornerShape(8.dp)
        )
    val icons = Icons(
        movie = R.drawable.movie,
        search = R.drawable.baseline_search_24,
        clear = R.drawable.clear,
        arrowBack = R.drawable.baseline_arrow_back_24
    )

    CompositionLocalProvider(
        LOCAL_COLORS provides colors,
        LOCAL_ICONS provides icons,
        LocalShapes provides shapes,
        LOCAL_TYPOGRAPHY provides typography,
        content = content
    )


}

object MovieNightTheme {
    val colors: Colors
        @Composable
        get() = LOCAL_COLORS.current
    val shapes: Shapes
        @Composable
        get() = LocalShapes.current
    val typography: Typography
        @Composable
        get() = LOCAL_TYPOGRAPHY.current
    val icons: Icons
        @Composable
        get() = LOCAL_ICONS.current
}