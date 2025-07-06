package org.wahid.movienight.domain.usecase

data class DetailScreenUseCases(
    val searchMovie: SearchMovie,
    val isFavoriteMovieUseCase: IsFavoriteMovieUseCase,
    val saveFavoriteMovieUseCase: SaveFavoriteMovieUseCase,
    val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
)