package org.wahid.movienight.presentation.screen.search

data class SearchUiState(
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val error: String? = null
)