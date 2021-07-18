/*
 * Copyright (c) 2021. Shining-cat - Shiva Bernhard
 * This file is part of Everyday.
 *
 *     Everyday is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, version 3 of the License.
 *
 *     Everyday is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Everyday.  If not, see <https://www.gnu.org/licenses/>.
 */

package fr.shining_cat.blocketgithubapp.di

import android.content.Context
import fr.shining_cat.blocketgithubapp.BuildConfig
import fr.shining_cat.blocketgithubapp.commons.CommonConstants
import fr.shining_cat.blocketgithubapp.commons.CommonConstants.Companion.API_BASE_URL
import fr.shining_cat.blocketgithubapp.commons.CommonConstants.Companion.CONNECT_TIMEOUT_SECONDS
import fr.shining_cat.blocketgithubapp.commons.GithubToken.Companion.GITHUB_PERSONAL_TOKEN
import fr.shining_cat.blocketgithubapp.commons.CommonConstants.Companion.READ_TIMEOUT_SECONDS
import fr.shining_cat.blocketgithubapp.commons.Logger
import fr.shining_cat.blocketgithubapp.domain.FetchGithubReposUseCase
import fr.shining_cat.blocketgithubapp.remote.api.ApiServices
import fr.shining_cat.blocketgithubapp.repositories.converters.OwnerConverter
import fr.shining_cat.blocketgithubapp.repositories.converters.RepoConverter
import fr.shining_cat.blocketgithubapp.repositories.repos.GithubRepository
import fr.shining_cat.blocketgithubapp.repositories.repos.GithubRepositoryImpl
import fr.shining_cat.blocketgithubapp.screens.viewmodels.MainViewModel
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single { Logger() }

    factory {
        val cache: Cache = get()
        val logger: Logger = get()
        buildApiServices(API_BASE_URL, cache, logger)
    }

    single {
        Cache(get<Context>().cacheDir, CommonConstants.CACHE_SIZE)
    }


    // Repositories
    factory {
        GithubRepositoryImpl(
            get(),
            get(),
            get()
        ) as GithubRepository
    }
    // Converters
    factory {
        RepoConverter(
            get()
        )
    }
    factory {
        OwnerConverter()
    }
    //Usecases
    factory {
        FetchGithubReposUseCase(
            get(),
            get()
        )
    }
    //Viewmodels
    viewModel {
        MainViewModel(
            get(),
            get()
        )
    }
}

private fun buildApiServices(
    baseUrl: String,
    cache: Cache,
    logger: Logger
): ApiServices {
    return Retrofit.Builder()
        .client(buildOkHttpClient(true, cache))
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(ApiServices::class.java)
}

private fun buildOkHttpClient(secured: Boolean, cache: Cache): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .cache(cache)
        .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)


    if (BuildConfig.DEBUG) {
        builder.addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        })
    }

    if (secured) {
        builder.addInterceptor(object : Interceptor {
            //TODO: this is probably the place we will add the personal token header?
            private val mCredentials: String = Credentials.basic("token", GITHUB_PERSONAL_TOKEN)

            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()
                val authenticatedRequest = request.newBuilder()
                    .header("Authorization", mCredentials).build()
                return chain.proceed(authenticatedRequest)
            }
        })
    }

    return builder.build()
}