package com.ericdream.erictv.data.reposittory

import com.ericdream.erictv.data.api.NowApi
import com.ericdream.erictv.data.model.ChannelResult
import com.ericdream.erictv.data.model.network.ViuTvRequest
import com.ericdream.erictv.data.reposittory.interfaces.LiveLinkGenerator
import javax.inject.Inject

class LiveLinkGeneratorImpl @Inject constructor(private val nowApi: NowApi) : LiveLinkGenerator {

    override suspend fun getNow331Link(): ChannelResult {
        return getChannelResult {
            val channelResponse = nowApi.getNowTV(channelno = "331")
            channelResponse.asset.hls.adaptive[0]
        }
    }

    override suspend fun getNow332Link(): ChannelResult {
        return getChannelResult {
            val channelResponse = nowApi.getNowTV(channelno = "332")
            channelResponse.asset.hls.adaptive[0]
        }
    }

    override suspend fun getViuTVLink(): ChannelResult {
        val request = ViuTvRequest(
            channelno = "099",
            deviceId = "99999999"
        )
        return getChannelResult {
            val channelResponse = nowApi.getViuTV(request)
            channelResponse.asset.hls.adaptive[0]
        }
    }

    private inline fun getChannelResult(functionToGetLink: () -> String): ChannelResult {
        return try {
            ChannelResult.create(functionToGetLink.invoke())
        } catch (e: Exception) {
            ChannelResult.create(e)
        }
    }
}