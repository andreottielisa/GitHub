package com.api.github.domain.entities

internal data class PullRequestsEntity(
    val title: String,
    val description: String,
    val username: String,
    val avatarUrl: String,
    val createdDate: String
)