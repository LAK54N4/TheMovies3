package com.laksana.themovies.ui.tvshow

import com.laksana.themovies.data.local.entity.TvShowEntity

interface TvShowCallback {
    fun onItemClicked(data: TvShowEntity)
}