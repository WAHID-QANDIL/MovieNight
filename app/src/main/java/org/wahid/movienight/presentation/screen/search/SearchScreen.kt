package org.wahid.movienight.presentation.screen.search

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.wahid.movienight.domain.model.Movie
import org.wahid.movienight.presentation.core.LocalAnimatedVisibilityScope
import org.wahid.movienight.presentation.core.LocalSharedTransitionScope
import org.wahid.movienight.presentation.core.MovieCard
import org.wahid.movienight.ui.theme.MovieNightTheme

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    onBackClick: () -> Unit,
    onMovieClick: (Movie) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalAnimatedVisibilityScope.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = uiState.searchQuery,
                        onValueChange = { viewModel.onSearchQueryChange(it) },
                        placeholder = { Text("Search for movies...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(MovieNightTheme.icons.search),
                                contentDescription = "Search Icon"
                            )
                        },
                        trailingIcon = {
                            if (uiState.searchQuery.isNotEmpty()) {
                                IconButton(onClick = { viewModel.clearSearch() }) {
                                    Icon(
                                        painter = painterResource(MovieNightTheme.icons.clear),
                                        contentDescription = "Clear Search"
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = { keyboardController?.hide() }
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = MovieNightTheme.colors.background,
                            cursorColor = MovieNightTheme.colors.component,
                            textColor = MovieNightTheme.colors.text,
                            placeholderColor = MovieNightTheme.colors.subText
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(MovieNightTheme.icons.arrowBack),
                            contentDescription = "Back",
                            tint = MovieNightTheme.colors.text
                        )
                    }
                },
                backgroundColor = MovieNightTheme.colors.background,
                elevation = 0.dp
            )
        },
        backgroundColor = MovieNightTheme.colors.background
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (searchResults.isEmpty() && uiState.searchQuery.isNotEmpty()) {
                // No results
                if (uiState.isSearching) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MovieNightTheme.colors.component
                    )
                } else {
                    Text(
                        text = "No movies found",
                        style = MovieNightTheme.typography.titleMedium,
                        color = MovieNightTheme.colors.subText,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                    )
                }
            } else if (uiState.searchQuery.isEmpty()) {
                // Empty search
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(MovieNightTheme.icons.search),
                        contentDescription = null,
                        modifier = Modifier.size(72.dp),
                        tint = MovieNightTheme.colors.subText.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Search for your favorite movies",
                        style = MovieNightTheme.typography.titleLarge,
                        color = MovieNightTheme.colors.subText,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                // Search results
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(searchResults) { movie ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onMovieClick(movie) },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            MovieCard(
                                modifier = Modifier
                                    .width(100.dp)
                                    .aspectRatio(0.65f),
                                movie = movie,
                                sharedTransitionScope = sharedTransitionScope,
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                            Column(
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .weight(1f)
                            ) {
                                Text(
                                    text = movie.title,
                                    style = MovieNightTheme.typography.titleLarge,
                                    color = MovieNightTheme.colors.text
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = movie.releaseDate,
                                    style = MovieNightTheme.typography.bodyMedium,
                                    color = MovieNightTheme.colors.subText
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = movie.overview,
                                    style = MovieNightTheme.typography.bodySmall,
                                    color = MovieNightTheme.colors.subText,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}