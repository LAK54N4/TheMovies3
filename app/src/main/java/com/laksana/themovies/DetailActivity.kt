package com.laksana.themovies

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.laksana.themovies.data.local.entity.MovieEntity
import com.laksana.themovies.data.local.entity.TvShowEntity
import com.laksana.themovies.databinding.ActivityDetailBinding
import com.laksana.themovies.databinding.ActivityDetailTvShowBinding
import com.laksana.themovies.utils.Constants.IMAGE_ENDPOINT
import com.laksana.themovies.utils.Constants.POSTER_SIZE_W185
import com.laksana.themovies.utils.Constants.POSTER_SIZE_W780
import com.laksana.themovies.utils.Constants.setImage
import com.laksana.themovies.viewmodel.DetailViewModel
import com.laksana.themovies.viewmodel.ViewModelFactory
import java.util.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_TYPE = "extra_type"
        const val TYPE_MOVIES = "type_movies"
        const val TYPE_TVSHOW = "type_tvShow"
    }

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var detailTvBinding: ActivityDetailTvShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        detailTvBinding = ActivityDetailTvShowBinding.inflate(layoutInflater)

        val factory = ViewModelFactory.getInstance(this)
        detailViewModel = ViewModelProvider(this@DetailActivity, factory)[DetailViewModel::class.java]

        val data = intent.getIntExtra(EXTRA_DATA, 0)
        val type = intent.getStringExtra(EXTRA_TYPE)

        if (type.equals(TYPE_MOVIES, ignoreCase = true)) {
            setContentView(detailBinding.root)
            detailBinding.progressBar.visibility = View.GONE

            val toolbarLayout = detailBinding.toolbar
            setSupportActionBar(toolbarLayout)
            Objects.requireNonNull(supportActionBar!!).setDisplayHomeAsUpEnabled(true)
            detailBinding.toolbarLayout.setExpandedTitleColor(Color.TRANSPARENT)

            data.let {
                detailViewModel.getDetailMovie(data).observe(this, {
                    showToolbarTitle(it)
                    showMovie(it)
                })
            }

        }

        else if (type.equals(TYPE_TVSHOW, ignoreCase = true)) {
            setContentView(detailTvBinding.root)
            detailTvBinding.progressBar.visibility = View.GONE

            val toolbarLayout = detailTvBinding.detailToolbar
            setSupportActionBar(toolbarLayout)
            Objects.requireNonNull(supportActionBar!!).setDisplayHomeAsUpEnabled(true)
            detailTvBinding.collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT)

            data.let {
                detailViewModel.getDetailTvShow(data).observe(this@DetailActivity, {
                    showToolbarTitleTvShow(it)
                    showTv(it)
                })
            }
        }
    }

    private fun showTv(tv: TvShowEntity) {
        detailTvBinding.tvTitle.text = tv.tvShowName
        detailTvBinding.tvLanguageValue.text = tv.originalLanguage
        detailTvBinding.tvReleaseDateValue.text = tv.firstAirDate
        detailTvBinding.tvOverviewValue.text = tv.tvShowOverview
        detailTvBinding.tvVoteAverageValue.text = tv.tvVoteAverage.toString()
        detailTvBinding.tvVoteCountValue.text = tv.tvVoteCount.toString()
        setImage(this, IMAGE_ENDPOINT + POSTER_SIZE_W185+tv.tvShowPoster,
            detailTvBinding.imgDetailPoster)

        setImage(this, IMAGE_ENDPOINT + POSTER_SIZE_W780+ tv.tvShowBackdrop,
            detailTvBinding.imgBackground)

        detailViewModel.checkTvFavorite(tv.id).observe(this, {
            detailTvBinding.fab.setOnClickListener {
                detailViewModel.setFavoriteTv(tv)
            }
            setFavoriteTvState(it)
        })
    }

    private fun showMovie(movie: MovieEntity) {
        detailBinding.tvTitle.text = movie.title
        setImage(this, IMAGE_ENDPOINT+ POSTER_SIZE_W780+movie.movieBackdrop,
            detailBinding.imgBackground)
        setImage(this, IMAGE_ENDPOINT+ POSTER_SIZE_W185+ movie.moviePoster,
            detailBinding.imgDetailPoster)
        detailBinding.tvReleaseDateValue.text = movie.movieRelease
        detailBinding.tvOverviewValue.text = movie.overview
        detailBinding.tvLanguageValue.text = movie.originalLanguage
        detailBinding.tvVoteAverageValue.text = movie.voteAverage.toString()
        detailBinding.tvVoteCountValue.text = movie.voteCount.toString()

        detailViewModel.checkMovieFavorite(movie.movieId).observe(this, {
            detailBinding.fab.setOnClickListener {
                detailViewModel.setFavoriteMovie(movie)
            }
            setFavoriteMovieState(it)
        })
    }

    private fun setFavoriteMovieState(state: Boolean) {
        with(detailBinding) {
            if(state) {
                fab.setImageResource(R.drawable.ic_baseline_favorite_24)
                Toast.makeText(this@DetailActivity, "Added to favorite Movie", Toast.LENGTH_SHORT).show()
            } else {
                fab.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }
    }


    private fun setFavoriteTvState(state: Boolean) {
        with(detailTvBinding) {
            if(state) {
                fab.setImageResource(R.drawable.ic_baseline_favorite_24)
                Toast.makeText(this@DetailActivity, "Added to favorite Tv Show", Toast.LENGTH_SHORT).show()
            } else {
                fab.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }
    }


    private fun showToolbarTitleTvShow(it: TvShowEntity) {
        val collapsingToolbarLayout: CollapsingToolbarLayout = detailTvBinding.collapsingToolbar
        collapsingToolbarLayout.title = it.tvShowName
    }

    private fun showToolbarTitle(it: MovieEntity) {
        val collapsingToolbar: CollapsingToolbarLayout = detailBinding.toolbarLayout
        collapsingToolbar.title = it.title
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this@DetailActivity, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        finish()
    }
}