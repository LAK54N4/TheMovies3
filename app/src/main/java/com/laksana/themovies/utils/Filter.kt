package com.laksana.themovies.utils

import androidx.sqlite.db.SimpleSQLiteQuery
import com.laksana.themovies.utils.Constants.ASC
import com.laksana.themovies.utils.Constants.DEFAULT
import com.laksana.themovies.utils.Constants.DESC
import java.lang.StringBuilder

fun getMovieSorted(filter: String): SimpleSQLiteQuery {
    val simpleSQLiteQuery = StringBuilder().append("SELECT * FROM tb_movies WHERE is_favorite = 1 ")
    when(filter) {
        DEFAULT -> simpleSQLiteQuery.append("ORDER BY release_date DESC")
        ASC -> simpleSQLiteQuery.append("ORDER BY title ASC")
        DESC -> simpleSQLiteQuery.append("ORDER BY title DESC")
    }
    return SimpleSQLiteQuery(simpleSQLiteQuery.toString())
}

fun getTvSorted(filter: String): SimpleSQLiteQuery {
    val simpleSQLiteQuery = StringBuilder().append("SELECT * FROM tb_tvShows WHERE is_favorites = 1 ")
    when(filter) {
        DEFAULT -> simpleSQLiteQuery.append("ORDER BY first_air_date DESC")
        ASC -> simpleSQLiteQuery.append("ORDER BY name ASC")
        DESC -> simpleSQLiteQuery.append("ORDER BY name DESC")
    }
    return SimpleSQLiteQuery(simpleSQLiteQuery.toString())
}

