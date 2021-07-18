package fr.shining_cat.blocketgithubapp.remote.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoDto(

    @field:Json(name = "id") var id: Int?,
    @field:Json(name = "node_id") var nodeId: String?,
    @field:Json(name = "name") var name: String?,
    @field:Json(name = "owner") var owner: OwnerDto?,
    @field:Json(name = "description") var description: String?,
    @field:Json(name = "fork") var isForked: Boolean?,
    @field:Json(name = "url") var url: String?,
    @field:Json(name = "created_at") var createdAt: String?,
    @field:Json(name = "updated_at") var updatedAt: String?,
    @field:Json(name = "git_url") var gitUrl: String?,
    @field:Json(name = "ssh_url") var sshUrl: String?,
    @field:Json(name = "stargazers_count") var stargazersCount: Int?,
    @field:Json(name = "watchers_count") var watchersCount: Int?,
    @field:Json(name = "language") var language: String?,
    @field:Json(name = "open_issues_count") var openIssuesCount: Int?
)