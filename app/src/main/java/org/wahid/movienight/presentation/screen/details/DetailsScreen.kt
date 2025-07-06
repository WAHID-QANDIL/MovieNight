package org.wahid.movienight.presentation.screen.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.wahid.movienight.domain.model.Movie
import org.wahid.movienight.presentation.core.LocalAnimatedVisibilityScope
import org.wahid.movienight.presentation.core.LocalSharedTransitionScope
import org.wahid.movienight.presentation.core.SharedElementKey
import org.wahid.movienight.presentation.core.SharedElementType
import org.wahid.movienight.presentation.core.svgEnabledImageLoader
import org.wahid.movienight.ui.theme.MovieNightTheme
import org.wahid.movienight.utils.sharedTransition

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    movie: Movie,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalAnimatedVisibilityScope.current
    val scrollState = rememberScrollState()

    LaunchedEffect(movie) {
        viewModel.setMovie(movie)
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Backdrop image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.backdropPath)
                .crossfade(true)
                .build(),
            imageLoader = svgEnabledImageLoader(),
            contentScale = ContentScale.Crop,
            contentDescription = "Movie Backdrop",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        // Gradient overlay for readability
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MovieNightTheme.colors.background.copy(alpha = 0.8f),
                            MovieNightTheme.colors.background
                        )
                    )
                )
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(180.dp))

            // Movie poster with shared transition
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.posterPath)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .width(140.dp)
                        .aspectRatio(0.65f)
                        .clip(RoundedCornerShape(16.dp))
                        .sharedTransition(
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope
                        ) { sharedTransitionScope, animatedVisibilityScope ->
                            with(sharedTransitionScope) {
                                sharedBounds(
                                    sharedContentState = rememberSharedContentState(
                                        key = SharedElementKey(
                                            id = movie.id,
                                            type = SharedElementType.POSTER
                                        )
                                    ),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    boundsTransform = { _, _ ->
                                        tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                                    }
                                )
                            }
                        }
                )

                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                ) {
                    // Title with shared transition
                    Text(
                        text = movie.title,
                        style = MovieNightTheme.typography.titleVeryLarge,
                        color = MovieNightTheme.colors.text,
                        modifier = Modifier
                            .sharedTransition(
                                sharedTransitionScope = sharedTransitionScope,
                                animatedVisibilityScope = animatedVisibilityScope
                            ) { sharedTransitionScope, animatedVisibilityScope ->
                                with(sharedTransitionScope) {
                                    sharedBounds(
                                        sharedContentState = rememberSharedContentState(
                                            key = SharedElementKey(
                                                id = movie.id,
                                                type = SharedElementType.TITLE
                                            )
                                        ),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        boundsTransform = { _, _ ->
                                            tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                                        }
                                    )
                                }
                            }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Release date
                    Text(
                        text = "Released: ${movie.releaseDate}",
                        style = MovieNightTheme.typography.bodyLarge,
                        color = MovieNightTheme.colors.subText
                    )

                    // Rating
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = "${movie.voteAverage}/10",
                            style = MovieNightTheme.typography.titleMedium,
                            color = MovieNightTheme.colors.text
                        )
                        Text(
                            text = " (${movie.voteCount} votes)",
                            style = MovieNightTheme.typography.bodyMedium,
                            color = MovieNightTheme.colors.subText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Overview
            Text(
                text = "Overview",
                style = MovieNightTheme.typography.titleLarge,
                color = MovieNightTheme.colors.text
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = movie.overview,
                style = MovieNightTheme.typography.bodyLarge,
                color = MovieNightTheme.colors.text,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        // Top bar with back button and favorite button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

        }
    }
}