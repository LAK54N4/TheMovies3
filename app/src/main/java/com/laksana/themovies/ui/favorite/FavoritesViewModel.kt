package com.laksana.themovies.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.laksana.themovies.data.DataRepository
import com.laksana.themovies.data.local.entity.MovieEntity
import com.laksana.themovies.data.local.entity.TvShowEntity

class FavoritesViewModel(private val repository: DataRepository) : ViewModel() {

    fun getFavoriteListMovie(sort: String): LiveData<PagedList<MovieEntity>> = repository.getFavoriteListMovies(sort)

    fun getFavoriteListTvShow(sort: String): LiveData<PagedList<TvShowEntity>> = repository.getFavoriteListTvShows(sort)

    fun setMovie(movieEntity: MovieEntity) {
        repository.setFavoriteMovies(movieEntity)
    }

    fun setTV(tvShowEntity: TvShowEntity) {
        repository.setFavoriteTvShows(tvShowEntity)
    }

}