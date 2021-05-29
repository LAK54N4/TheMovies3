package com.laksana.themovies.di

import android.content.Context
import com.laksana.themovies.data.DataRepository
import com.laksana.themovies.data.local.LocalDataSource.Companion.getInstance
import com.laksana.themovies.data.local.room.MovieDatabase
import com.laksana.themovies.data.remote.RemoteDataSource
import com.laksana.themovies.utils.AppExecutors

object Injection {
    fun provideDataRepository(context: Context): DataRepository {

        val database = MovieDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = getInstance(database.movieDao())
        val appExecutors = AppExecutors()

        return DataRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}
