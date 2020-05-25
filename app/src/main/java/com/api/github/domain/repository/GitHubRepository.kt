package com.api.github.domain.repository

import com.api.github.data.model.PullRequestModelResponse
import com.api.github.data.model.SearchResponseModel
import io.reactivex.Single

internal interface GitHubRepository {

    @Throws(Exception::class)
    fun fetchRepo(page: Long): Single<SearchResponseModel>?


    @Throws(Exception::class)
    fun fetchPullRequests(owner: String, repository: String): Single<List<PullRequestModelResponse>>?
}