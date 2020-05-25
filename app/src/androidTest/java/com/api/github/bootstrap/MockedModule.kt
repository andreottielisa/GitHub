package com.api.github.bootstrap

import com.api.github.data.MockRepository
import com.api.github.domain.repository.GitHubRepository
import org.koin.dsl.module

internal val moduleMockedRepository = module {
    factory<GitHubRepository> { MockRepository() }
}