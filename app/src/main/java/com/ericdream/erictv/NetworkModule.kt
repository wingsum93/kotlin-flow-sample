package com.ericdream.erictv

import android.content.Context
import com.ericdream.erictv.data.api.NowApi
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkhttoClient(): OkHttpClient {
        return OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build()
    }

    @Provides
    @Singleton
    fun provideaRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideNowApi(retrofit: Retrofit): NowApi {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun getDataSourceFactory(@ApplicationContext context: Context): com.google.android.exoplayer2.upstream.DataSource.Factory {
        return DefaultDataSourceFactory(context, "intern")
    }
}