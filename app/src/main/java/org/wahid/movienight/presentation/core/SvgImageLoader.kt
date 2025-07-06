package org.wahid.movienight.presentation.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.decode.SvgDecoder

@Composable
fun svgEnabledImageLoader(): ImageLoader {
    return ImageLoader.Builder(LocalContext.current)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()
}
