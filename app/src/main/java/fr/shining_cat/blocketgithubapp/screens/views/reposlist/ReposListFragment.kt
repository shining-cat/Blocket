package fr.shining_cat.blocketgithubapp.screens.views.reposlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fr.shining_cat.blocketgithubapp.R
import fr.shining_cat.blocketgithubapp.commons.Logger
import fr.shining_cat.blocketgithubapp.databinding.FragmentReposListBinding
import fr.shining_cat.blocketgithubapp.repositories.RequestResult
import fr.shining_cat.blocketgithubapp.screens.viewmodels.MainViewModel
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ReposListFragment : Fragment() {

    private val LOG_TAG = ReposListFragment::class.java.name

    private val logger: Logger = get()
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val reposAdapter = ReposAdapter(logger)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val reposListFragmentBinding = FragmentReposListBinding.inflate(layoutInflater)
        //
        setUpReposListRecyclerView(reposListFragmentBinding)
        //
        setupObservers(reposListFragmentBinding)
        //
        showLoadingMessage(reposListFragmentBinding, true)
        mainViewModel.fetchGithubRepos()
        //
        return reposListFragmentBinding.root
    }

    private fun showLoadingMessage(
        reposListFragmentBinding: FragmentReposListBinding,
        showIt: Boolean
    ) {
        if (showIt) {
            reposListFragmentBinding.message.visibility = VISIBLE
            reposListFragmentBinding.message.text = getString(R.string.generic_LOADING)
        } else {
            reposListFragmentBinding.message.visibility = GONE
        }
    }

    private fun setUpReposListRecyclerView(reposListFragmentBinding: FragmentReposListBinding) {
        reposListFragmentBinding.reposListRecyclerView.apply {
            adapter = reposAdapter
            while (itemDecorationCount > 0) {
                removeItemDecorationAt(0)
            }
            addItemDecoration(RepoItemDecoration(resources.getDimensionPixelSize(R.dimen.single_margin)))
        }
        reposAdapter.setListener(
            object : ReposAdapter.ReposListListener {
                override fun onItemClicked(repoId: Int) {
                    val action = ReposListFragmentDirections.destinationRepoDetailsFragment(repoId)
                    findNavController().navigate(action)
                }
            }
        )

    }

    private fun setupObservers(reposListFragmentBinding: FragmentReposListBinding) {
        mainViewModel.reposLiveData.observe(
            viewLifecycleOwner,
            {
                showLoadingMessage(reposListFragmentBinding, false)
                when (it) {
                    is RequestResult.Error -> showErrorMessage(
                        reposListFragmentBinding,
                        it.errorResponse
                    )
                    else -> {
                        it as RequestResult.Success
                        if (it.result.isEmpty()) showEmptyListMessage(reposListFragmentBinding)
                        else {
                            reposListFragmentBinding.message.visibility = GONE
                            logger.d(
                                LOG_TAG,
                                "reposLiveData loading success: ${it.result.size} repositories"
                            )
                            reposAdapter.submitList(it.result)
                        }
                    }
                }
            }
        )
    }

    private fun showErrorMessage(
        reposListFragmentBinding: FragmentReposListBinding,
        errorResponse: String
    ) {
        reposListFragmentBinding.message.visibility = VISIBLE
        reposListFragmentBinding.message.text = errorResponse
    }

    private fun showEmptyListMessage(reposListFragmentBinding: FragmentReposListBinding) {
        reposListFragmentBinding.message.visibility = VISIBLE
        reposListFragmentBinding.message.text = getString(R.string.generic_EMPTY_LIST)

    }

}