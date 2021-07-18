package fr.shining_cat.blocketgithubapp.screens.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.shining_cat.blocketgithubapp.commons.Logger
import fr.shining_cat.blocketgithubapp.domain.FetchGithubReposUseCase
import fr.shining_cat.blocketgithubapp.models.Repo
import fr.shining_cat.blocketgithubapp.repositories.RequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val fetchGithubReposUseCase: FetchGithubReposUseCase,
    private val logger: Logger
) : ViewModel() {

    private val LOG_TAG = MainViewModel::class.java.name

    private val _reposLiveData = MutableLiveData<RequestResult<List<Repo>>>()
    val reposLiveData: LiveData<RequestResult<List<Repo>>> = _reposLiveData

    fun fetchGithubRepos() {
        viewModelScope.launch {
            val requestResult = withContext(Dispatchers.IO) {
                fetchGithubReposUseCase.execute()
            }
            _reposLiveData.value = requestResult
        }
    }

    fun getRepoDetails(repoId: Int): Repo? {
        logger.d(
            LOG_TAG,
            "getRepoDetails::request for details for repo ${repoId}, reposLiveData.value is ${reposLiveData.value}"
        )
        //we can only get here by querying a previously returned id, so we know it's safe to get it now
        val reposList = reposLiveData.value as RequestResult.Success<List<Repo>>
        return reposList.result.find { it.id == repoId }
    }

}