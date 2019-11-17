package com.ericdream.erictv.data.model

import com.ericdream.erictv.data.api.NowApi
import com.ericdream.erictv.http.RetrofitManager
import retrofit2.HttpException

class LiveChannel {
    var name: String = ""
    var iconLink: String = ""
    var link: String? = null

    var operator: () -> ChannelResult = { ChannelResult.SmapleError }

    suspend fun getLink(): ChannelResult {
        val nowApi = RetrofitManager.create<NowApi>()
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

    companion object {

        fun create(name: String = "", iconLink: String, link: String?): LiveChannel {
            return LiveChannel().apply {
                this.name = name
                this.link = link
                this.iconLink = iconLink
            }
        }
    }
}