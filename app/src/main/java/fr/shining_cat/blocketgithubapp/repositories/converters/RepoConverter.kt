package fr.shining_cat.blocketgithubapp.repositories.converters

import fr.shining_cat.blocketgithubapp.models.Repo
import fr.shining_cat.blocketgithubapp.remote.api.dto.RepoDto

class RepoConverter(
    private val ownerConverter: OwnerConverter
) {

    fun convertDtoToModel(repos: List<RepoDto>): List<Repo> {
        return repos.map {
            Repo(
                id = it.id ?: -1,
                nodeId = it.nodeId ?: "",
                name = it.name ?: "",
                owner = ownerConverter.convertDtoToModel(it.owner),
                description = it.description ?: "",
                isForked = it.isForked ?: false,
                url = it.url ?: "",
                createdAt = it.createdAt ?: "",
                updatedAt = it.updatedAt ?: "",
                gitUrl = it.gitUrl ?: "",
                sshUrl = it.sshUrl ?: "",
                stargazersCount = it.stargazersCount ?: -1,
                watchersCount = it.watchersCount ?: -1,
                language = it.language ?: "",
                openIssuesCount = it.openIssuesCount ?: -1
            )
        }
    }
}