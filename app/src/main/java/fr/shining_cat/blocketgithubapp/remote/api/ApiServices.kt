package fr.shining_cat.blocketgithubapp.remote.api

import fr.shining_cat.blocketgithubapp.remote.api.dto.RepoDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {

    @GET("/user/repos")
    suspend fun fetchRepos(): Response<List<RepoDto>>

}