package org.wahid.movienight.presentation.core

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.wahid.movienight.domain.model.Movie
import org.wahid.movienight.utils.sharedTransition

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieCard(
    modifier: Modifier = Modifier.Companion,
    movie: Movie,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null
    ) {

    val imageKey = remember { movie.id.toString() }
    val cornerSize = animatedVisibilityScope?.let {
        animatedVisibilityScope.transition.animateDp (
            label = "cornerSize",
            targetValueByState = {
                when (it) {
                    EnterExitState.PreEnter -> 24.dp
                    EnterExitState.Visible -> 16.dp
                    EnterExitState.PostExit -> 24.dp
                }
            },
            transitionSpec = {
                tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
            }
        )
    }

    Box{
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(movie.posterPath)
                .placeholderMemoryCacheKey(imageKey)
                .memoryCacheKey(imageKey)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = movie.title,
            clipToBounds = true,

            modifier = modifier
                .aspectRatio(0.6f)
                .sharedTransition(
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,

                ){
                    sharedTransitionScope,animatedVisibilityScope->
                    with (sharedTransitionScope){
                        sharedBounds(
                            sharedContentState = rememberSharedContentState(key = SharedElementKey(
                                id = movie.id,
                                type = SharedElementType.POSTER
                            )),
                            animatedVisibilityScope = animatedVisibilityScope,
                            clipInOverlayDuringTransition = OverlayClip(
                                RoundedCornerShape(cornerSize?.value ?: 16.dp)
                            ),
                            boundsTransform = {
                                _,_->
                                tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                            },
                            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                            enter = EnterTransition.None,
                            exit = ExitTransition.None
                        )
                    }
                }.clip(MaterialTheme.shapes.medium)
        )
    }



}