package com.tlpcraft.kmp.demo

import android.app.Application
import com.tlpcraft.kmp.demo.di.initKoin

class KmpDemoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
