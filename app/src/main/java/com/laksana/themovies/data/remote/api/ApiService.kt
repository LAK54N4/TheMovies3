package com.laksana.themovies.data.remote.api

import com.laksana.themovies.BuildConfig
import com.laksana.themovies.data.remote.response.MovieResponse
import com.laksana.themovies.data.remote.response.ResultsItem
import com.laksana.themovies.data.remote.response.TVShowResultsItem
import com.laksana.themovies.data.remote.response.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
        ): Call<MovieResponse<List<ResultsItem>>>

    @GET("tv/on_the_air")
    fun getTvShow(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
        ): Call<TvShowResponse<List<TVShowResultsItem>>>
/*
    @GET("movie/{movie_id}")
    fun getDetailsMovies(
            @Path("movie_id") movieId: Int,
            @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Call<MovieDetailResponse>

    @GET("tv/{tv_id}")
    fun getTvShowDetail(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Call<TvShowDetailResponse>
    
 */

}