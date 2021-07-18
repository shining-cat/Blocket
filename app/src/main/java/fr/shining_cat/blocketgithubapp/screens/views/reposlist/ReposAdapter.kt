package fr.shining_cat.blocketgithubapp.screens.views.reposlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import fr.shining_cat.blocketgithubapp.commons.Logger
import fr.shining_cat.blocketgithubapp.databinding.LayoutRepoViewholderBinding
import fr.shining_cat.blocketgithubapp.models.Repo

class ReposAdapter(
    private val logger: Logger
) : ListAdapter<Repo, RepoViewHolder>(RepoDiffCallback()) {

    private val LOG_TAG = ReposAdapter::class.java.name

    private var listener: ReposListListener? = null
    fun setListener(listener: ReposListListener) {
        this.listener = listener
    }

    interface ReposListListener {
        fun onItemClicked(repoId: Int)
    }

    override fun submitList(list: List<Repo>?) {
        logger.d(LOG_TAG, "submitList: list of ${list?.size} repos")
        super.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            LayoutRepoViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            logger
        )
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = getItem(position)
        holder.bindView(repo, listener)
    }

    private class RepoDiffCallback : DiffUtil.ItemCallback<Repo>() {

        override fun areItemsTheSame(
            oldItem: Repo,
            newItem: Repo
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Repo,
            newItem: Repo
        ): Boolean = oldItem == newItem
    }

}