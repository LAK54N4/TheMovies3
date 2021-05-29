package com.laksana.themovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laksana.themovies.data.DataRepository
import com.laksana.themovies.data.local.entity.MovieEntity
import com.laksana.themovies.data.local.entity.TvShowEntity

class DetailViewModel (private var repository: DataRepository): ViewModel() {

    var movieId = MutableLiveData<Int>()

    fun getDetailMovie(movieId: Int): LiveData<MovieEntity> {
        return repository.getMovieDetails(movieId)
    }

    fun getDetailTvShow(tvShowId: Int): LiveData<TvShowEntity>{
        return repository.getTvShowDetails(tvShowId)
    }

    fun setFavoriteMovie(movieEntity: MovieEntity) {
        repository.setFavoriteMovies(movieEntity)
    }

    fun setFavoriteTv(tvShowEntity: TvShowEntity) {
        repository.setFavoriteTvShows(tvShowEntity)
    }

    fun checkMovieFavorite(id: Int): LiveData<Boolean> {
        return repository.checkMovieFavorite(id)
    }

    fun checkTvFavorite(id: Int): LiveData<Boolean> {
        return repository.checkTvFavorite(id)
    }

}