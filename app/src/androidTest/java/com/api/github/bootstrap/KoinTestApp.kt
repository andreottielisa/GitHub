package com.api.github.bootstrap

import android.app.Application
import com.api.github.di.fragmentModules
import com.api.github.di.schedulerProviderModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class KoinTestApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@KoinTestApp)
            fragmentFactory()
            modules(fragmentModules, schedulerProviderModule,
                moduleMockedRepository
            )
        }
    }
}