package com.coolrinet.movies.di

import android.app.Application
import androidx.room.Room
import com.coolrinet.movies.data.MovieRepository
import com.coolrinet.movies.data.MovieRepositoryImpl
import com.coolrinet.movies.data.database.MovieDatabase
import com.coolrinet.movies.data.network.api.MovieSearchInterceptor
import com.coolrinet.movies.data.network.api.OmdbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

private const val DATABASE_NAME = "movies_to_watch"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMovieDatabase(application: Application): MovieDatabase {
        return Room.databaseBuilder(
            application,
            MovieDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideOmdbApi(application: Application): OmdbApi {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(MovieSearchInterceptor())
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(database: MovieDatabase, api: OmdbApi): MovieRepository {
        return MovieRepositoryImpl(api, database.movieDao)
    }
}