package com.api.github

import android.app.Application

internal class TestApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        setTheme(R.style.AppTheme)
    }

}