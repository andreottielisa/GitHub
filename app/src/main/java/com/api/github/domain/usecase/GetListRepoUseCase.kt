package com.api.github.domain.usecase

import com.api.github.data.model.SearchResponseModel
import com.api.github.commons.BaseUseCase
import com.api.github.domain.SchedulerProvider
import com.api.github.domain.entities.RepoGitHubEntity
import com.api.github.domain.entities.RepositoriesEntity
import com.api.github.domain.repository.GitHubRepository
import com.api.github.utils.STRING_DEFAULT

internal class GetListRepoUseCase(scheduler: SchedulerProvider, private val repository: GitHubRepository) :
    BaseUseCase<RepositoriesEntity>(scheduler) {

    fun execute(
        page: Long,
        onSuccess: (RepositoriesEntity) -> Unit,
        onError: (Int) -> Unit
    ) {
        repository
            .fetchRepo(page)
            ?.map { transformRepositoriesEntity(it) }
            ?.executeUseCase(onSuccess, onError)
    }

    private fun transformRepositoriesEntity(it: SearchResponseModel?) = RepositoriesEntity(
        totalCount = it?.totalCount ?: 0,
        items = it?.items?.map { repo ->
            RepoGitHubEntity(
                nameRepo = repo.nameRepo ?: STRING_DEFAULT,
                descriptionRepo = repo.description ?: STRING_DEFAULT,
                username = repo.owner?.username ?: STRING_DEFAULT,
                avatar = repo.owner?.avatarUrl ?: STRING_DEFAULT,
                forksCount = repo.forksCount?.toString() ?: STRING_DEFAULT,
                stargazersCount = repo.stargazersCount?.toString() ?: STRING_DEFAULT
            )
        } ?: arrayListOf()
    )
}