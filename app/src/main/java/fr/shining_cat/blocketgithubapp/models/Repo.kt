package fr.shining_cat.blocketgithubapp.models

data class Repo(
    val id: Int,
    val nodeId: String,
    val name: String,
    val owner: Owner,
    val description: String,
    val isForked: Boolean,
    val url: String,
    val createdAt: String,
    val updatedAt: String,
    val gitUrl: String,
    val sshUrl: String,
    val stargazersCount: Int,
    val watchersCount: Int,
    val language: String,
    val openIssuesCount: Int
)