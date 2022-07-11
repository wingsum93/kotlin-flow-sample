package com.ericdream.erictv.data.api

import com.ericdream.erictv.data.model.ChannelBO
import com.ericdream.erictv.data.model.NowBO
import com.ericdream.erictv.data.model.ViuTvIO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NowApi {

    @GET("https://d1jithvltpp1l1.cloudfront.net/getLiveURL")
    suspend fun getNowTV(
        //331 , 332
        @Query(value = "channelno") channelno: String,
        @Query(value = "mode") mode: String = "prod",
        @Query(value = "format") format: String = "HLS",
        @Query(value = "audioCode") audioCode: String = "",
        @Query(value = "callerReferenceNo") callerReferenceNo: String = "20140702122500",
    ): NowBO

    @POST("https://api.viu.now.com/p8/3/getLiveURL")
    suspend fun getViuTV(@Body input: ViuTvIO): ChannelBO

}