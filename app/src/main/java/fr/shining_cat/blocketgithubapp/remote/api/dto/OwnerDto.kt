package fr.shining_cat.blocketgithubapp.remote.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OwnerDto(

    @field:Json(name = "login") var login: String?,
    @field:Json(name = "id") var id: Int?,
    @field:Json(name = "node_id") var nodeId: String?,
    @field:Json(name = "avatar_url") var avatarUrl: String?

)
