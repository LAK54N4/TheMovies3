package com.laksana.themovies.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.laksana.themovies.data.DataRepository
import com.laksana.themovies.data.local.entity.MovieEntity
import com.laksana.themovies.data.local.entity.TvShowEntity
import com.laksana.themovies.utils.DataDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private val dummyMovie = DataDummy.listMoviesResponse()[0]
    private val movieId = dummyMovie.movieId
    private val dummyTvShow = DataDummy.listTvShowResponse()[0]
    private val tvShowId = dummyTvShow.id

    private lateinit var viewModel: DetailViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: DataRepository

    @Mock
    private lateinit var observerMovie: Observer<MovieEntity>

    @Mock
    private lateinit var observerTvShow: Observer<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(repository)
    }


    @Test
    fun getDetailMovie() {
        val movieDummy = MutableLiveData<MovieEntity>()
        movieDummy.value = dummyMovie

        `when`(repository.getMovieDetails(movieId)).thenReturn(movieDummy)
        val movie = viewModel.getDetailMovie(movieId).value

        assertNotNull(movie)
        assertEquals(dummyMovie.movieId, movie?.movieId)
        assertEquals(dummyMovie.title, movie?.title)
        assertEquals(dummyMovie.overview, movie?.overview)
        assertEquals(dummyMovie.moviePoster, movie?.moviePoster)
        assertEquals(dummyMovie.movieBackdrop, movie?.movieBackdrop)
        assertEquals(dummyMovie.originalLanguage, movie?.originalLanguage)
        assertEquals(dummyMovie.voteAverage, movie?.voteAverage)
        assertEquals(dummyMovie.voteCount, movie?.voteCount)

        viewModel.getDetailMovie(movieId).observeForever(observerMovie)
        verify(observerMovie).onChanged(dummyMovie)
    }

    @Test
    fun getDetailTvShow() {
        val tvDummy = MutableLiveData<TvShowEntity>()
        tvDummy.value = dummyTvShow

        `when`(repository.getTvShowDetails(tvShowId)).thenReturn(tvDummy)
        val tvShow = viewModel.getDetailTvShow(tvShowId).value

        assertNotNull(tvShow)
        assertEquals(dummyTvShow.id, tvShow?.id)
        assertEquals(dummyTvShow.tvShowName, tvShow?.tvShowName)
        assertEquals(dummyTvShow.tvShowOverview, tvShow?.tvShowOverview)
        assertEquals(dummyTvShow.tvShowPoster, tvShow?.tvShowPoster)
        assertEquals(dummyTvShow.tvShowBackdrop, tvShow?.tvShowBackdrop)
        assertEquals(dummyTvShow.originalLanguage, tvShow?.originalLanguage)
        assertEquals(dummyTvShow.tvVoteAverage, tvShow?.tvVoteAverage)
        assertEquals(dummyTvShow.tvVoteCount, tvShow?.tvVoteCount)

        viewModel.getDetailTvShow(tvShowId).observeForever(observerTvShow)
        verify(observerTvShow).onChanged(dummyTvShow)
    }
}
