package fr.shining_cat.blocketgithubapp.screens.views.reposlist

import androidx.recyclerview.widget.RecyclerView
import fr.shining_cat.blocketgithubapp.commons.Logger
import fr.shining_cat.blocketgithubapp.databinding.LayoutRepoViewholderBinding
import fr.shining_cat.blocketgithubapp.models.Repo

class RepoViewHolder(
    private val repoViewHolder: LayoutRepoViewholderBinding,
    private val logger: Logger
) : RecyclerView.ViewHolder(repoViewHolder.root) {

    private val LOG_TAG = RepoViewHolder::class.java.name

    fun bindView(repo: Repo, listener: ReposAdapter.ReposListListener?) {
        repoViewHolder.repoName.text = repo.name
        repoViewHolder.repoDescription.text = repo.description
        repoViewHolder.repoLanguage.text = repo.language
        //
        repoViewHolder.root.setOnClickListener {
            listener?.onItemClicked(repo.id)
        }
    }
}