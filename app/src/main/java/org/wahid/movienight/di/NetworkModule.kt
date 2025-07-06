package org.wahid.movienight.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import org.wahid.movienight.BuildConfig
import org.wahid.movienight.data.remote.api_service.MovieApiService
import org.wahid.movienight.utils.okHttpClient
import org.wahid.movienight.utils.retrofit
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    fun provideJson(): Json =
        Json {
            ignoreUnknownKeys = true
            encodeDefaults = false
            explicitNulls = false
            coerceInputValues = true
        }

    const val REQUEST_TIME_OUT = 10L

    @Provides
    @Singleton
    fun provideRetrofit(json: Json): Retrofit {
        return retrofit {
            baseUrl(BuildConfig.API_URL)
            okHttpClient {
                connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                writeTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                addNetworkInterceptor { chain ->
                    // Add network interceptor logic here
                    // For example, you can add headers or log requests
                    val originalRequest = chain.request()
                    val newRequest = originalRequest
                        .newBuilder()
                        .header("Authorization", "Bearer ${BuildConfig.API_KEY}")
                        .build()
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                    chain.proceed(newRequest)

                }
            }
            addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        }
    }

    @Provides
    @Singleton
    fun provideMoveApiService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }
}