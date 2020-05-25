package com.api.github.domain.usecase

import com.api.github.R
import com.api.github.TestSchedulerProvider
import com.api.github.data.model.PullRequestModelResponse
import com.api.github.domain.entities.PullRequestsEntity
import com.api.github.domain.repository.GitHubRepository
import com.api.github.mockedPullRequestModelResponse
import com.api.github.mockedPullRequestsEntity
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.net.SocketTimeoutException
import java.util.concurrent.CompletableFuture

internal class GetPullRequestsUseCaseTest {

    private lateinit var repository: GitHubRepository
    private lateinit var useCase: GetPullRequestsUseCase

    private val owner = "golang"
    private val repo = "go"

    @Before
    fun setupTest() {
        repository = mock()
        useCase = GetPullRequestsUseCase(TestSchedulerProvider(), repository)
    }

    @Test
    fun `Assert execute success`() {
        //Given
        val expected: List<PullRequestsEntity> =
            arrayListOf(mockedPullRequestsEntity, mockedPullRequestsEntity)
        val response: List<PullRequestModelResponse> = arrayListOf(
            mockedPullRequestModelResponse,
            mockedPullRequestModelResponse
        )
        whenever(repository.fetchPullRequests(any(), any())).doReturn(Single.just(response))
        val future = CompletableFuture<List<PullRequestsEntity>>()

        //When
        useCase.execute(owner, repo, {
            future.complete(it)
        }, { Assert.fail() })

        // Then:
        Assert.assertEquals(expected, future.get())
        verify(repository).fetchPullRequests(owner, repo)
    }

    @Test
    fun `Assert execute error`() {
        //Given
        whenever(repository.fetchPullRequests(any(), any()))
            .doReturn(Single.error(SocketTimeoutException("Error in request")))
        val future = CompletableFuture<Int>()

        //When
        useCase.execute(owner, repo, { Assert.fail() }, {
            future.complete(it)
        })

        // Then:
        Assert.assertEquals(R.string.error_connection, future.get())
        verify(repository).fetchPullRequests(owner, repo)
    }
}