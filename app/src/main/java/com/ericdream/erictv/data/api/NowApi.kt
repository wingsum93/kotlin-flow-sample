package com.ericdream.erictv.data.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NowApi {

    @POST("https://hkt-mobile-api.nowtv.now.com/09/1/getLiveURL")
    fun now311(@Body string: String): Call<String>
}