package com.api.github.data.repository

import com.api.github.data.model.PullRequestModelResponse
import com.api.github.data.model.SearchResponseModel
import com.api.github.data.remote.GitHubService
import com.api.github.domain.repository.GitHubRepository
import io.reactivex.Single

internal class GitHubRepositoryImp(private val service: GitHubService) : GitHubRepository {

    @Throws(Exception::class)
    override fun fetchRepo(page: Long): Single<SearchResponseModel>? =
        service.getSearch(page).firstOrError()

    @Throws(Exception::class)
    override fun fetchPullRequests(owner: String, repository: String): Single<List<PullRequestModelResponse>>? =
        service.getPulls(owner, repository).firstOrError()
}