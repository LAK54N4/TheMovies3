package com.laksana.themovies.ui.movie

import com.laksana.themovies.data.local.entity.MovieEntity

interface MovieCallback {
    fun onItemClicked(data: MovieEntity)
}