package com.ericdream.erictv

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

@HiltAndroidApp
class App() : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        // start Koin!
        startKoin {
            // Android context
            androidContext(this@App)
            // modules
            modules(appModule)
        }
        Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(this)
    }

}