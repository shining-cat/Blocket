package fr.shining_cat.blocketgithubapp.commons

class CommonConstants {

    companion object {
        const val API_BASE_URL = "https://api.github.com"

        // Error codes
        const val HTTP_ERROR_CODE_EXCEPTION = -100
        const val HTTP_ERROR_CODE_DTO_NULL = -101
        const val HTTP_ERROR_CODE_ENTITY_NULL = -102
        const val HTTP_ERROR_CODE_DATABASE = -103

        // Server
        const val CONNECT_TIMEOUT_SECONDS = 30L
        const val READ_TIMEOUT_SECONDS = 30L
        const val CACHE_SIZE = (5 * 1024 * 1024).toLong() // 5MB

    }
}