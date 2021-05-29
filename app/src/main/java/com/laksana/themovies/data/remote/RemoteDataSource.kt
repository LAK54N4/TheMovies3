package com.laksana.themovies.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.laksana.themovies.data.remote.api.ApiClient
import com.laksana.themovies.data.remote.response.MovieResponse
import com.laksana.themovies.data.remote.response.ResultsItem
import com.laksana.themovies.data.remote.response.TVShowResultsItem
import com.laksana.themovies.data.remote.response.TvShowResponse
import com.laksana.themovies.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import java.io.IOException

class RemoteDataSource {

    private val retrofit = ApiClient.getApiService()

    companion object {

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getNowPlayingMovies(): LiveData<ApiResponse<MovieResponse<List<ResultsItem>>>>{
        val resultMovie = MutableLiveData<ApiResponse<MovieResponse<List<ResultsItem>>>>()
        EspressoIdlingResource.increment()

        CoroutineScope(IO).launch {
            val client = retrofit.getNowPlayingMovies()
            val response = client.awaitResponse()

            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        resultMovie.postValue(ApiResponse.success(it))
                    }
                } else {
                    Log.d("cek","error")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        EspressoIdlingResource.decrement()
        return resultMovie
    }

    fun getTvShows(): LiveData<ApiResponse<TvShowResponse<List<TVShowResultsItem>>>> {
        val resultTVShow = MutableLiveData<ApiResponse<TvShowResponse<List<TVShowResultsItem>>>>()
        EspressoIdlingResource.increment()

        CoroutineScope(IO).launch {
            val client = ApiClient.getApiService().getTvShow()
            val response = client.awaitResponse()

            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        resultTVShow.postValue(ApiResponse.success(it))
                    }
                } else {
                    Log.d("cek","error")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        EspressoIdlingResource.decrement()

        return resultTVShow
    }

/*
    fun getMovieDetails(movieId: Int): LiveData<ApiResponse<MovieDetailResponse>> {
        val resultMovieDetail = MutableLiveData<ApiResponse<MovieDetailResponse>>()
        EspressoIdlingResource.increment()
        ////////////////////////////////
/*
        CoroutineScope(IO).launch {
            val client = ApiClient.getApiService().getDetailsMovies(movieId)
            val response = client.awaitResponse()
            try {
                if(response.isSuccessful){
                    response.body()?.let {
                        resultMovieDetail.postValue(ApiResponse.success(it))
                    }
                } else {
                    Log.d("error", "error")
                }
            } catch (e: IOException){ e.printStackTrace()}
        }
        //////////////////////////////////////
        EspressoIdlingResource.decrement()
*/
        retrofit.getDetailsMovies(movieId, BuildConfig.API_KEY)
                .enqueue(object : Callback<MovieDetailResponse> {
                    override fun onResponse(call: Call<MovieDetailResponse>, response: Response<MovieDetailResponse>) {
                        resultMovieDetail.value = ApiResponse.success(response.body()!!)
                        EspressoIdlingResource.decrement()
                    }
                    override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                        EspressoIdlingResource.decrement()
                    }
                })
        return resultMovieDetail
    }
*/


}



