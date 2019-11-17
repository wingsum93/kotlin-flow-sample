package com.ericdream.erictv

import android.app.Application
import com.facebook.stetho.Stetho
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App() : Application() {
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

    fun buildDataSourceFactory(): DataSource.Factory {
        return DefaultDataSourceFactory(this, "eric-tv")
    }
}