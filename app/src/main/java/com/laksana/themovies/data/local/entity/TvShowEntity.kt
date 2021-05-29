package com.laksana.themovies.data.local.entity
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName="tb_tvShows")
@Parcelize
data class TvShowEntity (

    @PrimaryKey
    @field: SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val tvShowName: String,

    @field:SerializedName("overview")
    val tvShowOverview: String,

    @ColumnInfo(name="first_air_date")
    @field:SerializedName("first_air_date")
    val firstAirDate: String,

    @field:SerializedName("original_language")
    val originalLanguage: String,

    @ColumnInfo(name = "poster_path")
    @field: SerializedName("poster_path")
    val tvShowPoster: String? = null,

    @ColumnInfo(name = "backdrop_path")
    @field: SerializedName("backdrop_path")
    val tvShowBackdrop: String? = null,

    @ColumnInfo(name = "is_favorites")
    var isFavorite: Boolean = false,

    @field:SerializedName("vote_average")
    val tvVoteAverage: Double,

    @field:SerializedName("vote_count")
    val tvVoteCount: Int

): Parcelable
