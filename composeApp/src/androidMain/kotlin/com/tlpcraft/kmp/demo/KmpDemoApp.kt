package com.tlpcraft.kmp.demo

import android.app.Application
import com.tlpcraft.kmp.demo.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class KmpDemoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(applicationContext)
        }
    }
}
