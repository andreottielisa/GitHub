package com.api.github.data.repository

import com.api.github.data.model.PullRequestModelResponse
import com.api.github.data.model.SearchResponseModel
import com.api.github.data.remote.GitHubService
import com.api.github.domain.repository.GitHubRepository
import com.api.github.mockedPullRequestModelResponse
import com.api.github.mockedSearchResponseModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

internal class GitHubRepositoryImpTest {

    private lateinit var repository: GitHubRepository
    private lateinit var service: GitHubService

    @Before
    fun init() {
        service = mock()
        repository = GitHubRepositoryImp(service)
    }

    @Test
    fun `Assert fetchRepo`() {
        // given:
        val page = 1L
        val expected = mockedSearchResponseModel

        whenever(service.getSearch(page)).doReturn(Observable.just(expected))
        val testObserver = TestObserver<SearchResponseModel>()

        // when:
        val repositoryRequest = repository.fetchRepo(page)
        repositoryRequest?.subscribeWith(testObserver)

        // then:
        testObserver.assertResult(expected)
        verify(service).getSearch(page)
        assertNotNull(repositoryRequest)

        testObserver.dispose()
    }

    @Test
    fun `Assert fetchPullRequests`() {
        // given:
        val owner = "golang"
        val repo = "go"
        val expected: List<PullRequestModelResponse> = arrayListOf(mockedPullRequestModelResponse, mockedPullRequestModelResponse)

        whenever(service.getPulls(owner, repo)).doReturn(Observable.just(expected))
        val testObserver = TestObserver<List<PullRequestModelResponse>>()

        // when:
        val repositoryRequest = repository.fetchPullRequests(owner, repo)
        repositoryRequest?.subscribeWith(testObserver)

        // then:
        testObserver.assertResult(expected)
        verify(service).getPulls(owner, repo)
        assertNotNull(repositoryRequest)

        testObserver.dispose()
    }


}