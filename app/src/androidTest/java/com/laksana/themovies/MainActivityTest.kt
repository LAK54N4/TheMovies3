package com.laksana.themovies

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.laksana.themovies.data.remote.api.ApiClient
import com.laksana.themovies.data.remote.response.ResultsItem
import com.laksana.themovies.data.remote.response.TVShowResultsItem
import com.laksana.themovies.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test


class MainActivityTest {

    private val clientMovie = ApiClient.getApiService().getNowPlayingMovies()
    private val clientTv = ApiClient.getApiService().getTvShow()

    private lateinit var movieList: List<ResultsItem>
    private lateinit var tvList: List<TVShowResultsItem>
    private lateinit var dummyMovie: ResultsItem
    private lateinit var dummyTv: TVShowResultsItem

    @Before
    fun setUp() {
        movieList = clientMovie.execute().body()!!.results
        tvList = clientTv.execute().body()!!.results
        dummyMovie = movieList[0]
        dummyTv = tvList[0]

        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadMovie() {
        onView(withId(R.id.recyclerview_movie))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(movieList.size))
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.navigation_movie))
        onView(withId(R.id.recyclerview_movie))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(movieList.size))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.img_background)).check(matches(isDisplayed()))
        onView(withId(R.id.img_detailPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_releaseDateValue)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_languageValue)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overviewValue)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_voteAverageValue)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_voteCountValue)).check(matches(isDisplayed()))
        onView(withId(R.id.fab)).perform(click())

        pressBack()
    }

    @Test
    fun loadTvShow() {
        onView(withId(R.id.navigation_tvshow)).perform(click())
        onView(withId(R.id.recyclerview_tvShow)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerview_tvShow)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(tvList.size))
    }

    @Test
    fun loadDetailTv() {
        onView(withId(R.id.navigation_tvshow)).perform(click())
        onView(withId(R.id.recyclerview_tvShow))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(tvList.size))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.img_background)).check(matches(isDisplayed()))
        onView(withId(R.id.img_detailPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_releaseDateValue)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_languageValue)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overviewValue)).check(matches(isDisplayed()))

        onView(withId(R.id.fab)).perform(click())
    }

    @Test
    fun loadFavorite() {
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withId(R.id.recyclerview_movie_favorite))
            .check(matches(isCompletelyDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }
}
