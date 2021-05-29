package com.laksana.themovies.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.laksana.themovies.data.local.entity.MovieEntity
import com.laksana.themovies.data.local.entity.TvShowEntity
import com.laksana.themovies.vo.Resource

interface DataSource {

    fun getNowPlayingMovies(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>>

    fun getMovieDetails(movieId: Int): LiveData<MovieEntity>

    fun getTvShowDetails(tvShowId: Int): LiveData<TvShowEntity>

    fun setFavoriteMovies(movie: MovieEntity)

    fun setFavoriteTvShows(tvShow: TvShowEntity)

    fun getFavoriteListMovies(sort: String): LiveData<PagedList<MovieEntity>>

    fun getFavoriteListTvShows(sort: String): LiveData<PagedList<TvShowEntity>>

}