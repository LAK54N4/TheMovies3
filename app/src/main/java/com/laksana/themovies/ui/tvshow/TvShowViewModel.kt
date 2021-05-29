package com.laksana.themovies.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.laksana.themovies.data.DataRepository
import com.laksana.themovies.data.local.entity.TvShowEntity
import com.laksana.themovies.vo.Resource

class TvShowViewModel(private val tvShowRepository: DataRepository) : ViewModel() {

    fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> =
            tvShowRepository.getTvShows()

}