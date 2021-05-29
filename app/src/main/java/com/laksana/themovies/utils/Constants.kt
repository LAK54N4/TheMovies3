package com.laksana.themovies.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.laksana.themovies.R
import com.laksana.themovies.data.local.entity.MovieEntity

object Constants {
    const val IMAGE_ENDPOINT = "https:/image.tmdb.org/t/p/"
    const val POSTER_SIZE_W185 = "w185"
    const val POSTER_SIZE_W780 = "w780"

    const val DEFAULT = "DEFAULT"
    const val ASC = "A - Z"
    const val DESC = "Z - A"

    fun setImage(context: Context, poster: String, imagePoster: ImageView){
        Glide.with(context).load(poster)
            .placeholder(R.drawable.ic_baseline_movie_24)
            .error(R.drawable.ic_baseline_error_24)
            .into(imagePoster)
    }

}