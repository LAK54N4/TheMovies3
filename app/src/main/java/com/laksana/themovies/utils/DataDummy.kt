package com.laksana.themovies.utils

import com.laksana.themovies.data.local.entity.MovieEntity
import com.laksana.themovies.data.local.entity.TvShowEntity
import com.laksana.themovies.data.remote.response.ResultsItem
import com.laksana.themovies.data.remote.response.TVShowResultsItem

object DataDummy {
    fun listMoviesResponse() : List<MovieEntity> {
        val listMovieResponse = ArrayList<MovieEntity>()

        listMovieResponse.add(
            MovieEntity(
                overview = "Five young mutants, just discovering their abilities while held in a secret facility against their will, fight to escape their past sins and save themselves.",
                originalLanguage = "en",
                title = "The New Mutants",
                moviePoster = "https://image.tmdb.org/t/p/original/xrI4EnZWftpo1B7tTvlMUXVOikd.jpg",
                movieBackdrop = "https://image.tmdb.org/t/p/original/2AFZyra0Ddwl2oBDhClvD1qSIL6.jpg",
                movieRelease = "2020-08-26",
                voteAverage = 6.4,
                movieId = 340102,
                voteCount = 1352,
            )
        )
        return listMovieResponse
    }

    fun listTvShowResponse() : List<TvShowEntity>{
        val listTvShowResponse = ArrayList<TvShowEntity>()

        listTvShowResponse.add(
            TvShowEntity(
                firstAirDate = "Nov 12, 2019",
                tvShowOverview = "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.",
                originalLanguage = "en",
                tvShowPoster = "https://image.tmdb.org/t/p/original/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg",
                tvShowBackdrop = "https://image.tmdb.org/t/p/original/o7qi2v4uWQ8bZ1tW3KI0Ztn2epk.jpg",
                tvShowName = "The Mandalorian",
                id = 11,
                tvVoteAverage = 10.0,
                tvVoteCount = 99
            )
        )
        return listTvShowResponse
    }
}