package com.laksana.themovies.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.laksana.themovies.data.local.entity.MovieEntity
import com.laksana.themovies.data.local.entity.TvShowEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM tb_movies")
    fun getMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tb_tvShows")
    fun getTvShows(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM tb_movies WHERE movieId = :movieId")
    fun getDetailMovieById(movieId: Int): LiveData<MovieEntity>

    @Query("SELECT * FROM tb_tvShows WHERE id = :tvShowId")
    fun getDetailTvShowById(tvShowId: Int): LiveData<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShows: List<TvShowEntity>)

    @Update
    fun updateMovies(movies: MovieEntity)

    @Update
    fun updateTvShows(tvShows: TvShowEntity)

    @Query("SELECT is_favorite FROM tb_movies WHERE movieId = :id")
    fun checkMovieFavorite(id: Int): LiveData<Boolean>

    @Query("SELECT is_favorites FROM tb_tvShows WHERE id = :id")
    fun checkTvFavorite(id: Int): LiveData<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailTvShow(tvShow: TvShowEntity)

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getFavoriteListMovie(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieEntity>

    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getFavoriteListTvShow(query: SupportSQLiteQuery): DataSource.Factory<Int, TvShowEntity>

}