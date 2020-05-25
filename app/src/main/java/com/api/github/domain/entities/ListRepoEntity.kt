package com.api.github.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class RepoGitHubEntity(
    val nameRepo: String,
    val descriptionRepo: String,
    val username: String,
    val avatar: String,
    val forksCount: String,
    val stargazersCount: String
) : Parcelable

internal data class RepositoriesEntity(
    val totalCount: Long,
    val items: List<RepoGitHubEntity>
)