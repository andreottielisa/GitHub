package com.api.github.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.api.github.domain.entities.RepoGitHubEntity
import com.api.github.domain.usecase.GetListRepoUseCase
import com.api.github.presentation.state.ErrorState
import com.api.github.presentation.state.ListRepoState

internal class ListRepoViewModel(private val useCase: GetListRepoUseCase) : ViewModel() {

    private val _listRepoState by lazy { MutableLiveData<ListRepoState>() }
    private val _errorState by lazy { MutableLiveData<ErrorState>() }

    private val cachedListRepo = arrayListOf<RepoGitHubEntity>()
    private var currentPage = 0L

    val state: LiveData<ListRepoState>
        get() = _listRepoState

    val stateError: LiveData<ErrorState>
        get() = _errorState

    override fun onCleared() {
        useCase.dispose()
        super.onCleared()
    }

    fun getListRepo() {
        currentPage++

        useCase.execute(currentPage, {
            cachedListRepo.addAll(it.items)
            _listRepoState.value = ListRepoState.SuccessRepoList(cachedListRepo, it.totalCount == currentPage)
        }, {
            _errorState.value = ErrorState.ShowError(it)
        })
    }
}