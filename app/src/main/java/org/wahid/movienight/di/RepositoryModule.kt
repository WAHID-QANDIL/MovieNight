package org.wahid.movienight.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.wahid.movienight.data.repository.MovieRepositoryImpl
import org.wahid.movienight.domain.repository.MovieRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}