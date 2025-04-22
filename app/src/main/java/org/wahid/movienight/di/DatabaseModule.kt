package org.wahid.movienight.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.wahid.movienight.data.local.db.MovieDatabase
import org.wahid.movienight.utils.roomDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): MovieDatabase = roomDatabase<MovieDatabase>(
        context = context,
        name = MovieDatabase.DATABASE_NAME
    ) {
        fallbackToDestructiveMigration(false)
    }
}