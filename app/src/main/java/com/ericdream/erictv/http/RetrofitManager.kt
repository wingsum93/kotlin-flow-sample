package com.ericdream.erictv.http

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitManager() {
    private var okHttpClient: OkHttpClient
    private var retrofit: Retrofit

    init {
        okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor()).build()
        retrofit = Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun getRetrofit(): Retrofit = retrofit

    companion object {

        private var instance: RetrofitManager? = null


        fun getDefault(): RetrofitManager {

            if (instance == null) {
                synchronized(RetrofitManager::class) {
                    if (instance == null) {
                        instance = RetrofitManager()
                    }
                }
            }
            return instance!!
        }

        inline fun <reified T> create(): T {
            return getDefault().getRetrofit().create<T>()
        }
    }
} 