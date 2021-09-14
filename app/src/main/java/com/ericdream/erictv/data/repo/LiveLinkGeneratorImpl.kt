package com.ericdream.erictv.data.repo

import com.ericdream.erictv.data.api.NowApi
import com.ericdream.erictv.data.model.ChannelResult
import com.ericdream.erictv.data.model.NowIO
import com.ericdream.erictv.data.model.ViuTvIO
import com.ericdream.erictv.http.RetrofitManager
import javax.inject.Inject

class LiveLinkGeneratorImpl @Inject constructor() : LiveLinkGenerator {

    val nowApi = RetrofitManager.create<NowApi>()

    override suspend fun getNow331Link(): ChannelResult {
        val io = NowIO()
        io.channelno = "331"
        return try {
            val bo = nowApi.getNowTV(io)
            val link = bo.asset.hls.adaptive[0]
            ChannelResult.create(link)
        } catch (e: Exception) {
            ChannelResult.create(e)
        }
    }

    override suspend fun getNow332Link(): ChannelResult {
        val io = NowIO()
        io.channelno = "332"
        return try {
            val bo = nowApi.getNowTV(io)
            val link = bo.asset.hls.adaptive[0]
            ChannelResult.create(link)
        } catch (e: Exception) {
            ChannelResult.create(e)
        }
    }

    override suspend fun getViuTVLink(): ChannelResult {
        val io = ViuTvIO()
        io.channelno = "099"
        io.deviceId = "99999999"
        return try {
            val bo = nowApi.getViuTV(io)
            val link = bo.asset.hls.adaptive[0]
            ChannelResult.create(link)
        } catch (e: Exception) {
            ChannelResult.create(e)
        }
    }
}