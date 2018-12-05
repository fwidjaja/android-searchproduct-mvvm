package com.example.nakama.searchproduct

import android.app.Application
import com.fwidjaja.newsaggregator.di.searchModules
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(searchModules))
    }
}