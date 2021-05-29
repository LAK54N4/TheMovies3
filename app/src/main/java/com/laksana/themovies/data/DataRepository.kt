package com.laksana.themovies.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.laksana.themovies.data.local.LocalDataSource
import com.laksana.themovies.data.local.entity.MovieEntity
import com.laksana.themovies.data.local.entity.TvShowEntity
import com.laksana.themovies.data.remote.ApiResponse
import com.laksana.themovies.data.remote.RemoteDataSource
import com.laksana.themovies.data.remote.response.*
import com.laksana.themovies.utils.AppExecutors
import com.laksana.themovies.utils.getMovieSorted
import com.laksana.themovies.utils.getTvSorted
import com.laksana.themovies.vo.Resource

class DataRepository (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors)
    : DataSource {

        companion object {
            @Volatile
            private var instance: DataRepository? = null

            fun getInstance(remoteData: RemoteDataSource, localData: LocalDataSource, appExecutors: AppExecutors): DataRepository =
                instance ?: synchronized(this) {
                    instance ?: DataRepository(remoteData, localData, appExecutors)
                }
        }

    override fun getNowPlayingMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, MovieResponse<List<ResultsItem>>>(appExecutors) {

            override fun saveCallResult(data: MovieResponse<List<ResultsItem>>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data.results) {
                    val movie = MovieEntity(
                            response.id,
                            response.title,
                            response.overview,
                            response.posterPath,
                            response.backdropPath,
                            response.releaseDate,
                            false,
                            response.originalLanguage,
                            response.voteAverage,
                            response.voteCount
                    )
                    Log.d("repository", movie.toString() )
                    movieList.add(movie)
                }
                localDataSource.insertMovies(movieList)
            }

            override fun createCall(): LiveData<ApiResponse<MovieResponse<List<ResultsItem>>>> {
                return remoteDataSource.getNowPlayingMovies()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                localDataSource.getNowPlayingMovies()
                val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build()
                return LivePagedListBuilder(localDataSource.getNowPlayingMovies(), config).build()
            }
        }.asLiveData()
    }

    override fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object : NetworkBoundResource<PagedList<TvShowEntity>, TvShowResponse<List<TVShowResultsItem>>>(appExecutors) {

            override fun saveCallResult(data: TvShowResponse<List<TVShowResultsItem>>) {
                val tvShowList = ArrayList<TvShowEntity>()
                for (response in data.results) {
                    val tvShow = TvShowEntity(
                            response.id,
                            response.name,
                            response.overview,
                            response.firstAirDate,
                            response.originalLanguage,
                            response.posterPath,
                            response.backdropPath,
                            false,
                            response.voteAverage,
                            response.voteCount
                    )
                    tvShowList.add(tvShow)
                }
                localDataSource.insertTvShows(tvShowList)
            }

            override fun createCall(): LiveData<ApiResponse<TvShowResponse<List<TVShowResultsItem>>>> {
                return remoteDataSource.getTvShows()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config= PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getTvShows(), config).build()
            }
        }.asLiveData()
    }

    override fun getMovieDetails(movieId: Int): LiveData<MovieEntity> {
        return localDataSource.getDetailMovies(movieId)
    }

    override fun getTvShowDetails(tvShowId: Int): LiveData<TvShowEntity> {
        return localDataSource.getDetailTvShows(tvShowId)
    }

    fun checkMovieFavorite(id:Int): LiveData<Boolean> = localDataSource.checkMovieFavorite(id)

    fun checkTvFavorite(id:Int): LiveData<Boolean> = localDataSource.checkTvFavorite(id)

    override fun setFavoriteMovies(movie: MovieEntity) {
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteMovie(movie)
        }
    }

    override fun setFavoriteTvShows(tvShow: TvShowEntity) {
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteTvShows(tvShow)
        }
    }

    override fun getFavoriteListMovies(sort: String): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(5)
            setPageSize(5)
        }.build()

        val query = getMovieSorted(sort)
        return LivePagedListBuilder(localDataSource.getFavoriteListMovies(query), config).build()
    }

    override fun getFavoriteListTvShows(sort: String): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(5)
            setPageSize(5)
        }.build()

        val query = getTvSorted(sort)
        return LivePagedListBuilder(localDataSource.getFavoriteListTvShows(query), config).build()
    }

}