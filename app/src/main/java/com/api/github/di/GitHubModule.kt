package com.api.github.di

import com.api.github.BuildConfig
import com.api.github.commons.SchedulerProviderImp
import com.api.github.data.remote.GitHubService
import com.api.github.data.repository.GitHubRepositoryImp
import com.api.github.domain.SchedulerProvider
import com.api.github.domain.repository.GitHubRepository
import com.api.github.presentation.fragment.ListRepoFragment
import com.api.github.presentation.fragment.PullRequestsFragment
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

internal val schedulerProviderModule = module {
    factory<SchedulerProvider> { SchedulerProviderImp() }
}

internal val apiModuleService = module {
    single { GsonBuilder().setLenient().create() }
    single<GitHubService> {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
            else HttpLoggingInterceptor.Level.NONE

        val okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(get()))
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        retrofit.create(GitHubService::class.java)
    }
}

internal val moduleGitHubRepository = module {
    factory<GitHubRepository> { GitHubRepositoryImp(get()) }
}

internal val fragmentModules = module {
    fragment { ListRepoFragment() }
    fragment { PullRequestsFragment() }
}