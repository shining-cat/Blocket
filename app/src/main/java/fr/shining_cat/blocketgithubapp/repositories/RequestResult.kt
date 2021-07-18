package fr.shining_cat.blocketgithubapp.repositories

sealed class RequestResult<out T> {
    data class Success<out T>(
        val result: T,
        var durationRequestMillis: Long = 0
    ) : RequestResult<T>()

    data class Error(
        val errorCode: Int,
        val errorResponse: String,
        val exception: Exception?
    ) : RequestResult<Nothing>() {

        override fun toString(): String {
            return "Error(errorCode=$errorCode, errorResponse='$errorResponse', exception=$exception)"
        }
    }
}