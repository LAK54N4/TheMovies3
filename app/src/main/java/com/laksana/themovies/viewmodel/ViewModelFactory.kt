package com.laksana.themovies.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.laksana.themovies.data.DataRepository
import com.laksana.themovies.di.Injection
import com.laksana.themovies.ui.favorite.FavoritesViewModel
import com.laksana.themovies.ui.movie.MovieViewModel
import com.laksana.themovies.ui.tvshow.TvShowViewModel

class ViewModelFactory constructor(private val dataRepository: DataRepository)
    : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideDataRepository(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(dataRepository) as T
            }

            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                TvShowViewModel(dataRepository) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(dataRepository) as T
            }

            modelClass.isAssignableFrom(FavoritesViewModel::class.java) -> {
                FavoritesViewModel(dataRepository) as T
            }

            else -> throw    Throwable("Unknown ViewModel class: "+ modelClass.name)
        }
    }
}