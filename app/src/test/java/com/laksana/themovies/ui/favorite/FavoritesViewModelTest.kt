package com.laksana.themovies.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.laksana.themovies.data.DataRepository
import com.laksana.themovies.data.local.entity.MovieEntity
import com.laksana.themovies.data.local.entity.TvShowEntity
import com.laksana.themovies.utils.Constants.DEFAULT
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelTest {

    private lateinit var viewModel: FavoritesViewModel

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: DataRepository

    @Mock
    private lateinit var observerMovie: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var observerTvShow: Observer<PagedList<TvShowEntity>>

    @Mock
    private lateinit var moviePagedList: PagedList<MovieEntity>

    @Mock
    private lateinit var tvShowPagedList: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = FavoritesViewModel(repository)
    }

    @Test
    fun getFavoriteListMovie() {
        val dummyMovie = moviePagedList
        `when`(dummyMovie.size).thenReturn(10)
        val movie = MutableLiveData<PagedList<MovieEntity>>()
        movie.value = dummyMovie

        `when`(repository.getFavoriteListMovies(DEFAULT)).thenReturn(movie)
        val movieEntity = viewModel.getFavoriteListMovie(DEFAULT).value
        verify(repository).getFavoriteListMovies(DEFAULT)
        assertNotNull(movieEntity)
        assertEquals(10, movieEntity?.size)

        viewModel.getFavoriteListMovie(DEFAULT).observeForever(observerMovie)
        verify(observerMovie).onChanged(dummyMovie)

    }

    @Test
    fun getFavoriteListTvShow() {
        val dummyTV = tvShowPagedList
        `when`(dummyTV.size).thenReturn(10)
        val tvShow = MutableLiveData<PagedList<TvShowEntity>>()
        tvShow.value = dummyTV

        `when`(repository.getFavoriteListTvShows(DEFAULT)).thenReturn(tvShow)
        val tvShowEntity = viewModel.getFavoriteListTvShow(DEFAULT).value
        verify(repository).getFavoriteListTvShows(DEFAULT)
        assertNotNull(tvShowEntity)
        assertEquals(10, tvShowEntity?.size)

        viewModel.getFavoriteListTvShow(DEFAULT).observeForever(observerTvShow)
        verify(observerTvShow).onChanged(dummyTV)

    }
}