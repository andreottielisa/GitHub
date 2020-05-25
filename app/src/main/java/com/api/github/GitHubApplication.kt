package com.api.github

import android.app.Application
import com.api.github.di.apiModuleService
import com.api.github.di.fragmentModules
import com.api.github.di.moduleGitHubRepository
import com.api.github.di.schedulerProviderModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

internal class GitHubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GitHubApplication)
            fragmentFactory()
            modules(fragmentModules, apiModuleService, moduleGitHubRepository, schedulerProviderModule)
        }
    }

}