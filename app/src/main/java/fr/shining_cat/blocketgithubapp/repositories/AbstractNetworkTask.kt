package fr.shining_cat.blocketgithubapp.repositories

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import fr.shining_cat.blocketgithubapp.commons.CommonConstants
import okhttp3.Headers
import retrofit2.Response

abstract class AbstractNetworkTask<RequestType, ResultType> {

    suspend fun execute(): RequestResult<ResultType> {
        when (val apiResult = fetchFromNetwork()) {
            is ApiResult.Error -> {
                return apiResult.requestResultError
            }

            is ApiResult.Success -> {
                val apiResultDto = apiResult.result

                return if (apiResultDto == null) {
                    RequestResult.Error(
                        CommonConstants.HTTP_ERROR_CODE_DTO_NULL,
                        "",
                        NullPointerException("fetchFromNetwork returns null")
                    )
                } else {
                    try {
                        saveApiResults(apiResultDto)
                        if (shouldLoadFromLocale()) {
                            manageLocaleLoading()
                        } else {
                            RequestResult.Success(
                                transformResponse(
                                    apiResultDto,
                                    apiResult.headers
                                ),
                                apiResult.durationRequestMillis
                            )
                        }
                    } catch (exception: Exception) {
                        RequestResult.Error(
                            CommonConstants.HTTP_ERROR_CODE_DATABASE,
                            "",
                            exception
                        )
                    }
                }
            }
        }
    }

    private suspend fun fetchFromNetwork(): ApiResult<RequestType> {
        try {
            val startTimeMillis = System.currentTimeMillis()
            val apiResponse = createCallAsync()
            return if (apiResponse.isSuccessful) {
                val durationRequestMillis = System.currentTimeMillis() - startTimeMillis
                val headers = apiResponse.headers()
                ApiResult.Success(
                    apiResponse.body(),
                    durationRequestMillis,
                    headers
                )
            } else {
                val errorBodyString = apiResponse.errorBody()?.string() ?: ""
                ApiResult.Error(
                    RequestResult.Error(
                        apiResponse.code(),
                        errorBodyString,
                        null
                    )
                )
            }
        } catch (exception: Exception) {
            return ApiResult.Error(
                RequestResult.Error(
                    CommonConstants.HTTP_ERROR_CODE_EXCEPTION,
                    "",
                    exception
                )
            )
        }
    }

    private suspend fun manageLocaleLoading(): RequestResult<ResultType> {
        val fromLocale = loadFromLocale()
        return if (fromLocale == null) {
            RequestResult.Error(
                CommonConstants.HTTP_ERROR_CODE_ENTITY_NULL,
                "",
                NullPointerException("loadFromLocale returns null")
            )
        } else {
            RequestResult.Success(fromLocale)
        }
    }

    @MainThread
    protected abstract fun shouldLoadFromLocale(): Boolean

    @MainThread
    protected abstract suspend fun createCallAsync(): Response<RequestType>

    @WorkerThread
    protected abstract suspend fun transformResponse(
        response: RequestType,
        headers: Headers?
    ): ResultType

    @WorkerThread
    protected abstract suspend fun saveApiResults(item: RequestType)

    @MainThread
    protected abstract suspend fun loadFromLocale(): ResultType?

}