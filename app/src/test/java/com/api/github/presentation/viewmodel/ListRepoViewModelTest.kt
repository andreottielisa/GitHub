package com.api.github.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.api.github.R
import com.api.github.domain.entities.RepositoriesEntity
import com.api.github.domain.usecase.GetListRepoUseCase
import com.api.github.mockedRepositoriesEntity
import com.api.github.presentation.state.ErrorState
import com.api.github.presentation.state.ListRepoState
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
class ListRepoViewModelTest : AutoCloseKoinTest() {

    private lateinit var viewModel: ListRepoViewModel
    private lateinit var useCase: GetListRepoUseCase

    private lateinit var observer: Observer<ListRepoState>
    private lateinit var observerError: Observer<ErrorState>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {
        observer = mock()
        observerError = mock()
        useCase = mock()

        startKoin {
            modules(module {
                factory { useCase }
                viewModel { ListRepoViewModel(get()) }
            })
        }

        viewModel = get()
        viewModel.state.observeForever(observer)
        viewModel.stateError.observeForever(observerError)
    }

    @Test
    fun `Assert hasObserves`() {
        assertNotNull(viewModel.state)
        assertNotNull(viewModel.stateError)

        assertTrue(viewModel.state.hasObservers())
        assertTrue(viewModel.stateError.hasObservers())
    }

    @Test
    fun `Assert getListRepo success`() {
        //Given
        val expectedState = ListRepoState.SuccessRepoList::class.java
        val onSuccess = argumentCaptor<(RepositoriesEntity) -> Unit>()
        val onError = argumentCaptor<(Int) -> Unit>()

        doAnswer {
            (it.arguments[1] as (RepositoriesEntity).() -> Unit).invoke(mockedRepositoriesEntity)
        }.`when`(useCase).execute(any(), onSuccess.capture(), onError.capture())

        //When
        viewModel.getListRepo()

        //Then
        MatcherAssert.assertThat(viewModel.state.value, IsInstanceOf(expectedState))
        verify(observerError, never()).onChanged(ErrorState.ShowError(any()))
        verify(observer).onChanged(
            ListRepoState.SuccessRepoList(
                mockedRepositoriesEntity.items,
                false
            )
        )
    }

    @Test
    fun `Assert getListRepo error`() {
        //Given
        val expectedState = ErrorState.ShowError::class.java
        val onSuccess = argumentCaptor<(RepositoriesEntity) -> Unit>()
        val onError = argumentCaptor<(Int) -> Unit>()

        doAnswer {
            (it.arguments[2] as (Int).() -> Unit).invoke(R.string.error_connection)
        }.`when`(useCase).execute(any(), onSuccess.capture(), onError.capture())

        //When
        viewModel.getListRepo()

        //Then
        MatcherAssert.assertThat(viewModel.stateError.value, IsInstanceOf(expectedState))
        verify(observerError).onChanged(ErrorState.ShowError(R.string.error_connection))
        verify(observer, never()).onChanged(
            ListRepoState.SuccessRepoList(
                mockedRepositoriesEntity.items,
                false
            )
        )
    }
}