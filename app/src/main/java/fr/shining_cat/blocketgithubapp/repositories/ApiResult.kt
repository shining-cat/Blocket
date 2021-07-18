package fr.shining_cat.blocketgithubapp.repositories

import okhttp3.Headers

sealed class ApiResult<out T> {

    data class Success<out T>(
        val result: T?,
        var durationRequestMillis: Long = 0,
        var headers: Headers? = null
    ) : ApiResult<T>()

    data class Error(val requestResultError: RequestResult.Error) : ApiResult<Nothing>()
}