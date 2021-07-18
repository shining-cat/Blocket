package fr.shining_cat.blocketgithubapp.domain

import fr.shining_cat.blocketgithubapp.commons.Logger
import fr.shining_cat.blocketgithubapp.models.Repo
import fr.shining_cat.blocketgithubapp.repositories.RequestResult
import fr.shining_cat.blocketgithubapp.repositories.repos.GithubRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FetchGithubReposUseCaseTest {

    @MockK
    private lateinit var mockGithubRepository: GithubRepository

    @MockK
    private lateinit var mockRequestResult: RequestResult<List<Repo>>

    @MockK
    private lateinit var mockLogger: Logger

    private lateinit var fetchGithubReposUseCase: FetchGithubReposUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        fetchGithubReposUseCase = FetchGithubReposUseCase(
            mockGithubRepository,
            mockLogger
        )
        coEvery { mockLogger.d(any(), any()) } answers {}
        coEvery { mockLogger.e(any(), any()) } answers {}
    }

    @Test
    fun `test execute with successful result`() {
        coEvery { mockGithubRepository.fetchGithubRepos() } returns mockRequestResult
        //
        val output = runBlocking { fetchGithubReposUseCase.execute() }
        //
        coVerify(exactly = 1) { mockGithubRepository.fetchGithubRepos() }
        Assert.assertEquals(
            output,
            mockRequestResult
        )
    }

    @Test
    fun `test execute with failing result`() {
        val errorResult = RequestResult.Error(
            1,
            "testing error response",
            null
        )
        coEvery { mockGithubRepository.fetchGithubRepos() } returns errorResult
        //
        val output = runBlocking { fetchGithubReposUseCase.execute() }
        //
        coVerify(exactly = 1) { mockGithubRepository.fetchGithubRepos() }
        Assert.assertEquals(
            output,
            errorResult
        )
        coVerify(exactly = 1) {
            mockLogger.e(
                FetchGithubReposUseCase::class.java.name,
                "execute::error while fetching repos from Github: testing error response"
            )
        }
    }

}