package com.ericdream.erictv.data.api

import com.ericdream.erictv.data.model.ChannelBO
import com.ericdream.erictv.data.model.NowIO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NowApi {

    @POST("https://hkt-mobile-api.nowtv.now.com/09/1/getLiveURL")
    fun getNowTV(@Body input: NowIO): Call<ChannelBO>

    @POST("https://api.viu.now.com/p8/2/getLiveURL")
    fun getViuTV(@Body string: String): Call<ChannelBO>


}