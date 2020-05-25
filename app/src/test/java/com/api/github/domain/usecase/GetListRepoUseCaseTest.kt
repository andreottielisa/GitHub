package com.api.github.domain.usecase

import com.api.github.R
import com.api.github.TestSchedulerProvider
import com.api.github.domain.entities.RepositoriesEntity
import com.api.github.domain.repository.GitHubRepository
import com.api.github.mockedRepositoriesEntity
import com.api.github.mockedSearchResponseModel
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CompletableFuture

internal class GetListRepoUseCaseTest {

    private lateinit var repository: GitHubRepository
    private lateinit var useCase: GetListRepoUseCase

    @Before
    fun setupTest() {
        repository = mock()
        useCase = GetListRepoUseCase(TestSchedulerProvider(), repository)
    }

    @Test
    fun `Assert execute success`() {
        //Given
        val expected = mockedRepositoriesEntity
        whenever(repository.fetchRepo(any())).doReturn(Single.just(mockedSearchResponseModel))
        val future = CompletableFuture<RepositoriesEntity>()

        //When
        useCase.execute(1L, {
            future.complete(it)
        }, { Assert.fail() })

        // Then
        Assert.assertEquals(expected, future.get())
        verify(repository).fetchRepo(1L)
    }

    @Test
    fun `Assert execute error`() {
        //Given
        whenever(repository.fetchRepo(any())).doReturn(Single.error(Exception("Error in request")))
        val future = CompletableFuture<Int>()

        //When
        useCase.execute(1L, { Assert.fail() }, {
            future.complete(it)
        })

        // Then:
        Assert.assertEquals(R.string.mr_error_list, future.get())
        verify(repository).fetchRepo(1L)
    }

}