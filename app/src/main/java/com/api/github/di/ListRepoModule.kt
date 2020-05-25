package com.api.github.di

import androidx.annotation.VisibleForTesting
import com.api.github.domain.usecase.GetListRepoUseCase
import com.api.github.presentation.viewmodel.ListRepoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@VisibleForTesting
internal val moduleListRepo = module {
    factory { GetListRepoUseCase(get(), get()) }
    viewModel { ListRepoViewModel(get()) }
}

private val providerListRepo by lazy { loadKoinModules(moduleListRepo) }
internal fun loadModuleListRepo() = providerListRepo
