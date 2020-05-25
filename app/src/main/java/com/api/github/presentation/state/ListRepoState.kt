package com.api.github.presentation.state

import com.api.github.domain.entities.RepoGitHubEntity

internal sealed class ListRepoState {
    data class SuccessRepoList(val items: List<RepoGitHubEntity>, val lastPage: Boolean) : ListRepoState()
}