package fr.shining_cat.blocketgithubapp.screens.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import fr.shining_cat.blocketgithubapp.commons.Logger
import fr.shining_cat.blocketgithubapp.domain.FetchGithubReposUseCase
import fr.shining_cat.blocketgithubapp.models.Repo
import fr.shining_cat.blocketgithubapp.repositories.RequestResult
import fr.shining_cat.blocketgithubapp.testutils.MainCoroutineScopeRule
import fr.shining_cat.blocketgithubapp.testutils.extensions.getValueForTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @MockK
    private lateinit var mockFetchGithubReposUseCase: FetchGithubReposUseCase

    @MockK
    private lateinit var mockLogger: Logger

    @MockK
    private lateinit var mockRequestResult: RequestResult<List<Repo>>

    @MockK
    private lateinit var mockRepo: Repo

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        //
        mainViewModel = MainViewModel(mockFetchGithubReposUseCase, mockLogger)
        //
        coEvery { mockLogger.d(any(), any()) } answers {}
        coEvery { mockLogger.e(any(), any()) } answers {}
    }

    @Test
    fun `test fetchGithubRepos`() {
        coEvery { mockFetchGithubReposUseCase.execute() } returns mockRequestResult
        //
        runBlocking {
            mainViewModel.fetchGithubRepos()
            delay(1000)
        }
        val actual = mainViewModel.reposLiveData.getValueForTest()
        Assert.assertEquals(
            mockRequestResult,
            actual
        )
    }

    @Test
    fun `test getRepoDetails`() {
        coEvery { mockFetchGithubReposUseCase.execute() } returns RequestResult.Success(
            listOf(mockRepo)
        )
        coEvery { mockRepo.id } returns 1234
        //
        runBlocking {
            mainViewModel.fetchGithubRepos()
            delay(1000)
        }
        val actual = mainViewModel.getRepoDetails(1234)
        Assert.assertEquals(
            mockRepo,
            actual
        )
    }

}