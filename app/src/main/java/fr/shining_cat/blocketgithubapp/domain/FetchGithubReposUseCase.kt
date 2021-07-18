package fr.shining_cat.blocketgithubapp.domain

import fr.shining_cat.blocketgithubapp.commons.Logger
import fr.shining_cat.blocketgithubapp.models.Repo
import fr.shining_cat.blocketgithubapp.repositories.RequestResult
import fr.shining_cat.blocketgithubapp.repositories.repos.GithubRepository

class FetchGithubReposUseCase(
    private val githubRepository: GithubRepository,
    private val logger: Logger
) {

    private val LOG_TAG = FetchGithubReposUseCase::class.java.name

    suspend fun execute(): RequestResult<List<Repo>> {
        val response = githubRepository.fetchGithubRepos()
        if (response is RequestResult.Error) {
            logger.e(
                LOG_TAG,
                "execute::error while fetching repos from Github: ${response.errorResponse}"
            )
        }
        return response
    }
}