package com.api.github.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.api.github.R
import com.api.github.domain.entities.PullRequestsEntity
import com.api.github.domain.usecase.GetPullRequestsUseCase
import com.api.github.mockedPullRequestsEntity
import com.api.github.mockedRepoGitHubEntity
import com.api.github.presentation.state.ErrorState
import com.api.github.presentation.state.LoadState
import com.api.github.presentation.state.PullRequestsState
import com.nhaarman.mockitokotlin2.*
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get

@Suppress("UNCHECKED_CAST")
class PullRequestsViewModelTest : AutoCloseKoinTest() {

    private lateinit var viewModel: PullRequestsViewModel
    private lateinit var useCase: GetPullRequestsUseCase

    private lateinit var observer: Observer<PullRequestsState>
    private lateinit var observerError: Observer<ErrorState>
    private lateinit var observerLoad: Observer<LoadState>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {
        observer = mock()
        observerError = mock()
        observerLoad = mock()
        useCase = mock()

        startKoin {
            modules(module {
                factory { useCase }
                viewModel { PullRequestsViewModel(get()) }
            })
        }

        viewModel = get()
        viewModel.state.observeForever(observer)
        viewModel.stateError.observeForever(observerError)
        viewModel.stateLoad.observeForever(observerLoad)
    }

    @Test
    fun `Assert hasObserves`() {
        assertNotNull(viewModel.state)
        assertNotNull(viewModel.stateError)
        assertNotNull(viewModel.stateLoad)

        assertTrue(viewModel.state.hasObservers())
        assertTrue(viewModel.stateError.hasObservers())
        assertTrue(viewModel.stateLoad.hasObservers())
    }

    @Test
    fun `Assert getPullRequests success`() {
        //Given
        val expectedState = PullRequestsState.SuccessPullRequests::class.java
        val expectedObjects = arrayListOf(mockedPullRequestsEntity)
        val onSuccess = argumentCaptor<(List<PullRequestsEntity>) -> Unit>()
        val onError = argumentCaptor<(Int) -> Unit>()

        doAnswer {
            (it.arguments[2] as (List<PullRequestsEntity>).() -> Unit)
                .invoke(expectedObjects)
        }.`when`(useCase).execute(any(), any(), onSuccess.capture(), onError.capture())

        //When
        viewModel.getPullRequests(mockedRepoGitHubEntity)

        //Then
        MatcherAssert.assertThat(viewModel.state.value, IsInstanceOf(expectedState))
        verify(observerError, never()).onChanged(ErrorState.ShowError(any()))
        verify(observer).onChanged(PullRequestsState.SuccessPullRequests(expectedObjects))
        verify(observerLoad).onChanged(LoadState.ShowLoading)
        verify(observerLoad).onChanged(LoadState.HideLoading)
    }

    @Test
    fun `Assert getPullRequests success empty response`() {
        //Given
        val expectedState = PullRequestsState.EmptyPullRequests::class.java
        val expectedObjects = arrayListOf<PullRequestsEntity>()
        val onSuccess = argumentCaptor<(List<PullRequestsEntity>) -> Unit>()
        val onError = argumentCaptor<(Int) -> Unit>()

        doAnswer {
            (it.arguments[2] as (List<PullRequestsEntity>).() -> Unit)
                .invoke(expectedObjects)
        }.`when`(useCase).execute(any(), any(), onSuccess.capture(), onError.capture())

        //When
        viewModel.getPullRequests(mockedRepoGitHubEntity)

        //Then
        MatcherAssert.assertThat(viewModel.state.value, IsInstanceOf(expectedState))
        verify(observerError, never()).onChanged(ErrorState.ShowError(any()))
        verify(observer).onChanged(PullRequestsState.EmptyPullRequests)
        verify(observerLoad).onChanged(LoadState.ShowLoading)
        verify(observerLoad).onChanged(LoadState.HideLoading)
    }

    @Test
    fun `Assert getPullRequests error`() {
        //Given
        val expectedState = ErrorState.ShowError::class.java
        val onSuccess = argumentCaptor<(List<PullRequestsEntity>) -> Unit>()
        val onError = argumentCaptor<(Int) -> Unit>()

        doAnswer {
            (it.arguments[3] as (Int).() -> Unit)
                .invoke(R.string.mr_error_list)
        }.`when`(useCase).execute(any(), any(), onSuccess.capture(), onError.capture())

        //When
        viewModel.getPullRequests(mockedRepoGitHubEntity)

        //Then
        MatcherAssert.assertThat(viewModel.stateError.value, IsInstanceOf(expectedState))
        verify(observerError).onChanged(ErrorState.ShowError(R.string.mr_error_list))
        verify(observerLoad).onChanged(LoadState.ShowLoading)
        verify(observerLoad).onChanged(LoadState.HideLoading)
        verify(observer, never()).onChanged(PullRequestsState.EmptyPullRequests)
        verify(observer, never()).onChanged(PullRequestsState.SuccessPullRequests(arrayListOf()))
    }
}