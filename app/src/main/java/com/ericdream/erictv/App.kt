package com.ericdream.erictv

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App() : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(this)
    }

    override fun attachBaseContext(base: Context?) {
        MultiDex.install(this)
        super.attachBaseContext(base)
    }
}