package com.api.github.di

import androidx.annotation.VisibleForTesting
import com.api.github.domain.usecase.GetPullRequestsUseCase
import com.api.github.presentation.viewmodel.PullRequestsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@VisibleForTesting
internal val modulePullRequests = module {
    factory { GetPullRequestsUseCase(get(), get()) }
    viewModel { PullRequestsViewModel(get()) }
}

private val providerPullRequests by lazy { loadKoinModules(modulePullRequests) }
internal fun loadModulePullRequests() = providerPullRequests
