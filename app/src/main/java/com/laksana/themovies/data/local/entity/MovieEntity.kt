package com.laksana.themovies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName="tb_movies")
data class MovieEntity(
    @PrimaryKey
    @field:SerializedName("id")
    var movieId: Int,

    @field: SerializedName("title")
    var title: String,

    @field: SerializedName("overview")
    var overview: String,

    @ColumnInfo(name = "poster_path")
    @field: SerializedName("poster_path")
    var moviePoster: String,

    @field: SerializedName("backdrop_path")
    var movieBackdrop: String,

    @ColumnInfo(name = "release_date")
    @field: SerializedName ("release_date")
    var movieRelease: String,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false,

    @field:SerializedName("original_language")
    val originalLanguage: String,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("vote_count")
    val voteCount: Int

)