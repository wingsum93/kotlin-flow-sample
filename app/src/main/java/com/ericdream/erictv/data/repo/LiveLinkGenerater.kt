package com.ericdream.erictv.data.repo

import com.ericdream.erictv.data.api.NowApi
import com.ericdream.erictv.data.model.ChannelResult
import com.ericdream.erictv.data.model.NowIO
import com.ericdream.erictv.http.RetrofitManager
import retrofit2.HttpException

class LiveLinkGenerater() {

    val nowApi = RetrofitManager.create<NowApi>()


    suspend fun getLink(key: String): ChannelResult {

        val io = NowIO()
        io.channelno = "331"
        val call = nowApi.getNowTV(io)
        try {
            val res = call.execute()
            if (!res.isSuccessful) {
                throw HttpException(res)
            }
            val bo = res.body()!!
            val link = bo.asset.hls.adaptive[0]
            return ChannelResult.create(link)
        } catch (e: Exception) {
            return ChannelResult.create(e)
        }
    }


}