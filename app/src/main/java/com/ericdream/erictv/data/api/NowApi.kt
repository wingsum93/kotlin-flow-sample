package com.ericdream.erictv.data.api

import com.ericdream.erictv.data.model.ChannelBO
import com.ericdream.erictv.data.model.NowIO
import com.ericdream.erictv.data.model.ViuTvIO
import retrofit2.http.Body
import retrofit2.http.POST

interface NowApi {

    @POST("https://hkt-mobile-api.nowtv.now.com/09/1/getLiveURL")
    suspend fun getNowTV(@Body input: NowIO): ChannelBO

    @POST("https://api.viu.now.com/p8/2/getLiveURL")
    suspend fun getViuTV(@Body input: ViuTvIO): ChannelBO


}