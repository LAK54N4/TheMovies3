package com.laksana.themovies.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList

import com.laksana.themovies.data.DataRepository
import com.laksana.themovies.data.local.entity.MovieEntity
import com.laksana.themovies.vo.Resource

class MovieViewModel (private val movieRepository: DataRepository)
    : ViewModel() {

        fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> = movieRepository.getNowPlayingMovies()
}