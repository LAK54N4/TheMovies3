package com.laksana.themovies.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.laksana.themovies.data.local.entity.MovieEntity
import com.laksana.themovies.data.local.entity.TvShowEntity
import com.laksana.themovies.data.local.room.MovieDao

class LocalDataSource private constructor(private val movieDao: MovieDao) {

    companion object {
        private  var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = LocalDataSource(movieDao)
            }
            return INSTANCE as LocalDataSource
        }
    }

    fun getNowPlayingMovies(): DataSource.Factory<Int, MovieEntity> = movieDao.getMovies()
    fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovies(movies)

    fun getTvShows(): DataSource.Factory<Int, TvShowEntity> = movieDao.getTvShows()
    fun insertTvShows(tvShows: List<TvShowEntity>) = movieDao.insertTvShows(tvShows)

    fun getDetailMovies(movieId: Int): LiveData<MovieEntity> = movieDao.getDetailMovieById(movieId)

    fun getDetailTvShows(tvShowId: Int): LiveData<TvShowEntity> = movieDao.getDetailTvShowById(tvShowId)

    fun setFavoriteMovie(movie: MovieEntity){
        movie.isFavorite = !movie.isFavorite
        movieDao.updateMovies(movie)
    }

    fun setFavoriteTvShows(tvShow: TvShowEntity) {
        tvShow.isFavorite = !tvShow.isFavorite
        movieDao.updateTvShows(tvShow)
    }

    fun checkMovieFavorite(id: Int): LiveData<Boolean> = movieDao.checkMovieFavorite(id)

    fun checkTvFavorite(id: Int): LiveData<Boolean> = movieDao.checkTvFavorite(id)

    fun getFavoriteListMovies(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieEntity> =
        movieDao.getFavoriteListMovie(query)

    fun getFavoriteListTvShows(query: SupportSQLiteQuery): DataSource.Factory<Int, TvShowEntity> =
        movieDao.getFavoriteListTvShow(query)

}