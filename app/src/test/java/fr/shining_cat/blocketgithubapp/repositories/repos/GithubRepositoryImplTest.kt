package fr.shining_cat.blocketgithubapp.repositories.repos

import fr.shining_cat.blocketgithubapp.commons.CommonConstants
import fr.shining_cat.blocketgithubapp.commons.Logger
import fr.shining_cat.blocketgithubapp.models.Repo
import fr.shining_cat.blocketgithubapp.remote.api.ApiServices
import fr.shining_cat.blocketgithubapp.remote.api.dto.RepoDto
import fr.shining_cat.blocketgithubapp.repositories.RequestResult
import fr.shining_cat.blocketgithubapp.repositories.converters.RepoConverter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import okhttp3.Headers
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GithubRepositoryImplTest {

    @MockK
    private lateinit var mockApiServices: ApiServices

    @MockK
    private lateinit var mockRepoConverter: RepoConverter

    @MockK
    private lateinit var mockLogger: Logger

    @MockK
    private lateinit var mockResponse: Response<List<RepoDto>>

    @MockK
    private lateinit var mockHeaders: Headers

    @MockK
    private lateinit var mockResponseBody: ResponseBody

    @MockK
    private lateinit var mockRepoDto: RepoDto

    @MockK
    private lateinit var mockRepo: Repo

    private lateinit var githubRepository: GithubRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        githubRepository = GithubRepositoryImpl(
            mockApiServices,
            mockRepoConverter,
            mockLogger
        )
        coEvery { mockLogger.d(any(), any()) } answers {}
        coEvery { mockLogger.e(any(), any()) } answers {}
    }

    @Test
    fun `test fetchGithubRepos ok`() {
        coEvery {
            mockApiServices.fetchRepos()
        } returns mockResponse
        coEvery { mockResponse.isSuccessful } returns true
        coEvery { mockResponse.body() } returns listOf(mockRepoDto)
        coEvery { mockResponse.headers() } returns mockHeaders
        coEvery { mockRepoConverter.convertDtoToModel(any()) } returns listOf(mockRepo)
        //
        val resultTested = runBlocking { githubRepository.fetchGithubRepos() }
        //
        Assert.assertTrue(resultTested is RequestResult.Success)
        resultTested as RequestResult.Success
        assertEquals(listOf(mockRepo), resultTested.result)
        coVerify(exactly = 1) { mockApiServices.fetchRepos() }
        coVerify(exactly = 1) { mockRepoConverter.convertDtoToModel(any()) }
    }

    @Test
    fun `test fetchGithubRepos api returns error `() {
        coEvery { mockApiServices.fetchRepos() } returns mockResponse
        coEvery { mockResponse.isSuccessful } returns false
        coEvery { mockResponse.code() } returns 99999
        coEvery { mockResponse.errorBody() } returns mockResponseBody
        coEvery { mockResponseBody.string() } returns "response body raw string"
        coEvery { mockRepoConverter.convertDtoToModel(any()) } returns listOf(mockRepo)
        //
        val resultTested = runBlocking { githubRepository.fetchGithubRepos() }
        //
        Assert.assertTrue(resultTested is RequestResult.Error)
        assertEquals(
            99999,
            (resultTested as RequestResult.Error).errorCode
        )
        assertEquals(
            "response body raw string",
            resultTested.errorResponse
        )
        Assert.assertNull(resultTested.exception)
        coVerify(exactly = 1) { mockApiServices.fetchRepos() }
    }

    @Test
    fun `test fetchBookmarks api throwsException`() {
        coEvery { mockApiServices.fetchRepos() } throws NullPointerException()
        //
        val resultTested = runBlocking { githubRepository.fetchGithubRepos() }
        //
        Assert.assertTrue(resultTested is RequestResult.Error)
        assertEquals(
            CommonConstants.HTTP_ERROR_CODE_EXCEPTION,
            (resultTested as RequestResult.Error).errorCode
        )
        assertEquals(
            "",
            (resultTested as RequestResult.Error).errorResponse
        )
        Assert.assertTrue((resultTested as RequestResult.Error).exception is NullPointerException)
        coVerify(exactly = 1) { mockApiServices.fetchRepos() }
    }
}