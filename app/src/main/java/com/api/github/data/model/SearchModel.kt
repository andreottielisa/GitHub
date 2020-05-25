package com.api.github.data.model

import com.google.gson.annotations.SerializedName

internal data class SearchResponseModel(
    @SerializedName("total_count") val totalCount: Long? = null,
    @SerializedName("items") val items: List<Repo>? = null
)

internal data class Repo(
    @SerializedName("id") val id: Long?,
    @SerializedName("name") val nameRepo: String?,
    @SerializedName("full_name") val fullNameRepo: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("forks_count") val forksCount: Long?,
    @SerializedName("stargazers_count") val stargazersCount: Long?,
    @SerializedName("owner") val owner: Owner?
)

internal data class Owner(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("login") val username: String? = null,
    @SerializedName("avatar_url") val avatarUrl: String? = null
)