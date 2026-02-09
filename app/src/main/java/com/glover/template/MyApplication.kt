package com.glover.template

import android.app.Application
import com.glover.template.di.appModule
import com.glover.template.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}