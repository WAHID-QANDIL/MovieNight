package org.wahid.movienight.presentation.screen.home


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope.ResizeMode
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.FlowPreview
import org.wahid.movienight.R
import org.wahid.movienight.domain.model.Movie
import org.wahid.movienight.presentation.core.DockedSearchBarScaffold
import org.wahid.movienight.presentation.core.LocalAnimatedVisibilityScope
import org.wahid.movienight.presentation.core.LocalSharedTransitionScope
import org.wahid.movienight.presentation.core.MovieCard
import org.wahid.movienight.presentation.core.SharedElementKey
import org.wahid.movienight.presentation.core.SharedElementType
import org.wahid.movienight.presentation.core.svgEnabledImageLoader
import org.wahid.movienight.ui.theme.MovieNightTheme
import org.wahid.movienight.utils.sharedTransition

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onClickSearch: () -> Unit = {},
    onClickMovie: (Movie, String) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreenContent(
        uiState = uiState,
        onClickMovie = { _, _ -> },
        onClickSearch = {}
    )

}


@RequiresApi(Build.VERSION_CODES.S)
@Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class,
    ExperimentalFoundationApi::class,
    FlowPreview::class,
    ExperimentalMaterialApi::class
)
@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onClickSearch: () -> Unit,
    onClickMovie: (Movie, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalAnimatedVisibilityScope.current

    val movies = uiState.movies.collectAsLazyPagingItems()
    val trendingMovies: List<Movie> = uiState.trendingMovies

    Log.d("trending", "trending: ${uiState.trendingMovies}")
    Log.d("trending", "trending: $trendingMovies")


    var currentItem by remember { mutableIntStateOf(0) }
    var backdropPath by remember { mutableStateOf("") }

    var manualRefresh by remember(movies) { mutableStateOf(false) }

    val isLoading by remember {
        derivedStateOf { !movies.loadState.isIdle && !movies.loadState.hasError }
    }

    val hasError by remember {
        derivedStateOf { movies.loadState.hasError }
    }

    val pullRefresh = rememberPullRefreshState(
        refreshing = manualRefresh && isLoading,
        onRefresh = {
            manualRefresh = true
            movies.refresh()
        }
    )

    LaunchedEffect(isLoading) {
        if (!isLoading) {
            manualRefresh = false
        }
    }

    val mostTrendingFiveMovies = listOf(
        R.drawable.one,
        R.drawable.two,
        R.drawable.three,
        R.drawable.four,
        R.drawable.five,
    )


    Box(
        modifier = modifier
            .pullRefresh(pullRefresh)
            .fillMaxSize(),
    ) {
        val backgroundColor = MovieNightTheme.colors.background
        AsyncImage(
            model =
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(backdropPath)
                    .crossfade(true)
                    .build(),
            imageLoader = svgEnabledImageLoader(),
            contentScale = ContentScale.Companion.Crop,
            contentDescription = "Icon",
            modifier =
                Modifier
                    .height(350.dp)
                    .fillMaxWidth()
                    .graphicsLayer { alpha = 0.99f }
                    .drawWithContent {
                        val colors =
                            listOf(
                                backgroundColor,
                                Color.Transparent
                            )
                        drawContent()
                        drawRect(
                            brush =
                                Brush.linearGradient(
                                    colors = colors,
                                    start = Offset(0f, 0f),
                                    end = Offset(0f, size.height)
                                ),
                            blendMode = BlendMode.DstIn
                        )
                    }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        )
        {
            stickyHeader(key = 1) {
                DockedSearchBarScaffold()
                Spacer(modifier = Modifier.height(100.dp))
            }
            item(key = 2) {
                Text(
                    text = stringResource(R.string.trending),
                    modifier =
                        Modifier
                            .padding(
                                top = 24.dp,
                                start = 24.dp,
                                end = 24.dp
                            ),
                    style = MovieNightTheme.typography.titleVeryLarge,
                    color = MovieNightTheme.colors.text
                )

            }



            if (hasError) {
                item(key = 3) {
                    Text(
                        uiState.error?:"Unknown Error",
                        color = MovieNightTheme.colors.error
                    )
                }
            } else {
                item(key = 4) {
                    if (trendingMovies.isNotEmpty()) {
                        val carouselState = rememberCarouselState(
                            initialItem = 0,
                            itemCount = {
                                trendingMovies.size
                            }
                        )
                        @Suppress("INVISIBLE_MEMBER")
                        LaunchedEffect(carouselState.pagerState.currentPage) {

                            backdropPath =
                                trendingMovies[carouselState.pagerState.currentPage].backdropPath
                                    ?: ""

//                        snapshotFlow { carouselState.pagerState.currentPage }
//                            .debounce(300.milliseconds)
//                            .collect { page ->
//                                if (trendingMovies.itemSnapshotList.isNotEmpty()) {
//                                    trendingMovies.itemSnapshotList[page]?.let { movie ->
//                                        backdropPath = movie.backdropPath.orEmpty()
//                                    }
//                                }
//                            }
                        }

                        HorizontalMultiBrowseCarousel(
                            modifier = Modifier.fillMaxWidth(),
                            state = carouselState,
                            preferredItemWidth = 150.dp,
                            itemSpacing = 16.dp,
                            contentPadding = PaddingValues(horizontal = 24.dp)
                        ) { page ->
                            LaunchedEffect(Unit) {
                                backdropPath = trendingMovies[0].backdropPath ?: ""
                            }


                            if (page < trendingMovies.size) {
                                currentItem = page
                                trendingMovies[page].let { movie ->
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Box {
                                            MovieCard(
                                                modifier =
                                                    Modifier
                                                        .fillMaxSize()
                                                        .aspectRatio(0.65f)
                                                        .clickable(
                                                            interactionSource = remember { MutableInteractionSource() },
                                                            indication = null,
                                                            onClick =
                                                                dropUnlessResumed {
                                                                    onClickMovie(movie, "trending")
                                                                }
                                                        )
                                                        .maskClip(MovieNightTheme.shapes.medium)
                                                        .animateItem(),
                                                sharedTransitionScope = sharedTransitionScope,
                                                animatedVisibilityScope = animatedVisibilityScope,
                                                movie = movie
                                            )
                                            Image(
                                                modifier = Modifier
                                                    .align(Alignment.BottomStart)
                                                    .size(50.dp),
                                                painter = painterResource(mostTrendingFiveMovies[currentItem]),
                                                contentDescription = "Icon",
                                                contentScale = ContentScale.Fit
                                            )
                                        }
                                        Spacer(modifier = Modifier.padding(top = 8.dp))
                                        Text(
                                            text = movie.title,
                                            style = MovieNightTheme.typography.titleLarge,
                                            color = MovieNightTheme.colors.text,
                                            modifier =
                                                Modifier
                                                    .sharedTransition(
                                                        sharedTransitionScope = sharedTransitionScope,
                                                        animatedVisibilityScope = animatedVisibilityScope
                                                    ) { sharedTransitionScope, animatedVisibilityScope ->
                                                        with(sharedTransitionScope) {
                                                            sharedBounds(
                                                                sharedContentState =
                                                                    rememberSharedContentState(
                                                                        key =
                                                                            SharedElementKey(
                                                                                id = movie.id,
                                                                                type = SharedElementType.TITLE
                                                                            )
                                                                    ),
                                                                animatedVisibilityScope = animatedVisibilityScope,
                                                                resizeMode = ResizeMode.ScaleToBounds(),
                                                                boundsTransform = { _, _ ->
                                                                    tween(
                                                                        durationMillis = 2000,
                                                                        easing = LinearOutSlowInEasing
                                                                    )
                                                                }
                                                            )
                                                        }
                                                            .align(Alignment.CenterHorizontally)
                                                            .padding(top = 4.dp)
                                                    },
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Center
                                        )
                                    }


                                }
                            }

                        }
                        Spacer(Modifier.height(24.dp))
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                item(key = 5) {
                    val gridState = rememberLazyStaggeredGridState()
                    LazyVerticalStaggeredGrid(
                        modifier = Modifier.padding(horizontal = 8.dp)
                            .heightIn(min = 100.dp, max = 600.dp),
                        state = gridState,
                        columns = StaggeredGridCells.Fixed(3),
                        reverseLayout = false,
                        verticalItemSpacing = 24.dp,
                        horizontalArrangement = Arrangement.spacedBy(24.dp),

                        ) {
                        items(
                            movies.itemCount,
                            key = { movies[it]?.id ?: it }
                        ) { index ->
                            val movie = movies[index]
                            movie?.let {
                                MovieCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(0.65f)
                                        .clickable {
                                            onClickMovie(movie, "Movies")
                                        }
                                        .clip(MovieNightTheme.shapes.medium)
                                        .animateItem(),
                                    sharedTransitionScope = sharedTransitionScope,
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    movie = movie
                                )
                                Text(
                                    text = movie.title,
                                    style = MovieNightTheme.typography.titleLarge,
                                    color = MovieNightTheme.colors.text,
                                    modifier =
                                        Modifier
                                            .sharedTransition(
                                                sharedTransitionScope = sharedTransitionScope,
                                                animatedVisibilityScope = animatedVisibilityScope
                                            ) { sharedTransitionScope, animatedVisibilityScope ->
                                                with(sharedTransitionScope) {
                                                    sharedBounds(
                                                        sharedContentState =
                                                            rememberSharedContentState(
                                                                key =
                                                                    SharedElementKey(
                                                                        id = movie.id,
                                                                        type = SharedElementType.TITLE
                                                                    )
                                                            ),
                                                        animatedVisibilityScope = animatedVisibilityScope,
                                                        resizeMode = ResizeMode.ScaleToBounds(),
                                                        boundsTransform = { _, _ ->
                                                            tween(
                                                                durationMillis = 2000,
                                                                easing = LinearOutSlowInEasing
                                                            )
                                                        }
                                                    )
                                                }
                                                    .padding(top = 4.dp)
                                            },
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center
                                )
                            }

                        }
                    }
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefresh,
            modifier =
                Modifier
                    .size(48.dp)
                    .align(Alignment.TopCenter),
            backgroundColor = MovieNightTheme.colors.background,
            contentColor = MovieNightTheme.colors.text
        )
    }


}