package com.api.github.presentation.state

import com.api.github.domain.entities.PullRequestsEntity

internal sealed class PullRequestsState {
    data class SuccessPullRequests(val items: List<PullRequestsEntity>) : PullRequestsState()
    object EmptyPullRequests : PullRequestsState()
}