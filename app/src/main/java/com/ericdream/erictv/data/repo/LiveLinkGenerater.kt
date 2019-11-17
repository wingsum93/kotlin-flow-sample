package com.ericdream.erictv.data.repo

import com.ericdream.erictv.data.api.NowApi
import com.ericdream.erictv.data.model.ChannelResult
import com.ericdream.erictv.data.model.NowIO
import com.ericdream.erictv.http.RetrofitManager

class LiveLinkGenerater() {

    val nowApi = RetrofitManager.create<NowApi>()


    suspend fun getLink(key: String): ChannelResult {

        val io = NowIO()
        io.channelno = "331"
        try {
            val bo = nowApi.getNowTV(io)
            val link = bo.asset.hls.adaptive[0]
            return ChannelResult.create(link)
        } catch (e: Exception) {
            return ChannelResult.create(e)
        }
    }


}