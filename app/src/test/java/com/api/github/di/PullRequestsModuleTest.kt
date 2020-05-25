package com.api.github.di

import com.api.github.TestSchedulerProvider
import com.api.github.data.remote.GitHubService
import com.api.github.domain.SchedulerProvider
import com.api.github.domain.repository.GitHubRepository
import com.api.github.domain.usecase.GetPullRequestsUseCase
import com.api.github.presentation.viewmodel.PullRequestsViewModel
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

internal class PullRequestsModuleTest : AutoCloseKoinTest(){

    @Before
    fun setupTest(){
        val appModuleTest = module {
            single<SchedulerProvider> { TestSchedulerProvider() }
            single<Gson> { mock { Gson::class.java }  }
            single<GitHubService> { mock { GitHubService::class.java }  }
            single<GitHubRepository> { mock { GitHubRepository::class.java } }
        }

        startKoin { modules(appModuleTest, modulePullRequests) }
    }

    @Test
    fun `Assert if useCase is provide by module`(){
        val useCase by inject<GetPullRequestsUseCase>()
        Assert.assertNotNull(useCase)
    }

    @Test
    fun `Assert if viewModel is provide by module`(){
        val viewModel by inject<PullRequestsViewModel>()
        Assert.assertNotNull(viewModel)
    }
}