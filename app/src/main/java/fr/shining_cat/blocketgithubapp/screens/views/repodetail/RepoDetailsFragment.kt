package fr.shining_cat.blocketgithubapp.screens.views.repodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fr.shining_cat.blocketgithubapp.R
import fr.shining_cat.blocketgithubapp.commons.Logger
import fr.shining_cat.blocketgithubapp.databinding.FragmentRepoDetailsBinding
import fr.shining_cat.blocketgithubapp.models.Repo
import fr.shining_cat.blocketgithubapp.screens.viewmodels.MainViewModel
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.sharedViewModel

class RepoDetailsFragment : Fragment() {

    private val LOG_TAG = RepoDetailsFragment::class.java.name

    private val logger: Logger = get()
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val args: RepoDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val reposListFragmentBinding = FragmentRepoDetailsBinding.inflate(layoutInflater)
        //
        val reposDetails = mainViewModel.getRepoDetails(args.repoId)
        logger.d(LOG_TAG, "repo details for ${reposDetails?.name}")
        initUi(reposListFragmentBinding, reposDetails)
        //
        return reposListFragmentBinding.root
    }

    private fun initUi(reposListFragmentBinding: FragmentRepoDetailsBinding, reposDetails: Repo?) {
        setupBackButton(reposListFragmentBinding)
        if (reposDetails == null) {
            val builder = AlertDialog.Builder(reposListFragmentBinding.root.context)
            builder.setTitle(getString(R.string.generic_ERROR))
            builder.setMessage(getString(R.string.repo_detail_error))
            builder.show()
        } else {
            reposListFragmentBinding.repoName.text = reposDetails.name
            reposListFragmentBinding.repoDescription.text = reposDetails.description
            reposListFragmentBinding.repoLanguage.text = reposDetails.language
            reposListFragmentBinding.owner.text = reposDetails.owner.login
            reposListFragmentBinding.isForked.text = reposDetails.isForked.toString()
            reposListFragmentBinding.url.text = reposDetails.url
            reposListFragmentBinding.createdAt.text = reposDetails.createdAt
            reposListFragmentBinding.updatedAt.text = reposDetails.updatedAt
            reposListFragmentBinding.gitUrl.text = reposDetails.gitUrl
            reposListFragmentBinding.sshUrl.text = reposDetails.sshUrl
            reposListFragmentBinding.stargazersCount.text = reposDetails.stargazersCount.toString()
            reposListFragmentBinding.watchersCount.text = reposDetails.watchersCount.toString()
            reposListFragmentBinding.openIssuesCount.text = reposDetails.openIssuesCount.toString()
        }
    }

    private fun setupBackButton(reposListFragmentBinding: FragmentRepoDetailsBinding) {
        reposListFragmentBinding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}