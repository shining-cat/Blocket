package fr.shining_cat.blocketgithubapp.repositories.repos

import fr.shining_cat.blocketgithubapp.commons.Logger
import fr.shining_cat.blocketgithubapp.models.Repo
import fr.shining_cat.blocketgithubapp.remote.api.ApiServices
import fr.shining_cat.blocketgithubapp.remote.api.dto.RepoDto
import fr.shining_cat.blocketgithubapp.repositories.AbstractNetworkTask
import fr.shining_cat.blocketgithubapp.repositories.RequestResult
import fr.shining_cat.blocketgithubapp.repositories.converters.RepoConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Headers
import retrofit2.Response

interface GithubRepository {
    suspend fun fetchGithubRepos(): RequestResult<List<Repo>>
}

class GithubRepositoryImpl(
    private var apiServices: ApiServices,
    private val repoConverter: RepoConverter,
    private val logger: Logger
) : GithubRepository {

    override suspend fun fetchGithubRepos(): RequestResult<List<Repo>> {
        return object : AbstractNetworkTask<List<RepoDto>, List<Repo>>() {
            override fun shouldLoadFromLocale(): Boolean {
                return false
            }

            override suspend fun createCallAsync(): Response<List<RepoDto>> =
                withContext(Dispatchers.IO) {
                    apiServices.fetchRepos()
                }

            override suspend fun transformResponse(
                response: List<RepoDto>,
                headers: Headers?
            ): List<Repo> {
                return repoConverter.convertDtoToModel(response)
            }

            override suspend fun saveApiResults(item: List<RepoDto>) {
            }

            override suspend fun loadFromLocale(): List<Repo>? {
                return null
            }
        }.execute()
    }
}