package com.laksana.themovies.data.remote

class ApiResponse<T> (val status: StatusResponse, val body: T, val message: String?) {

    companion object {
        fun<T> success(body: T): ApiResponse<T> = ApiResponse(StatusResponse.SUCCESS, body, null)

        fun <T> empty(message: String, body: T): ApiResponse<T> = ApiResponse(StatusResponse.EMPTY, body, message)

        fun<T> error(message: String, body: String): ApiResponse<String> = ApiResponse(StatusResponse.ERROR, body, message)

    }
}

