package com.ericdream.erictv.data.repo

import android.util.Log
import com.ericdream.erictv.data.api.NowApi
import com.ericdream.erictv.data.model.ChannelResult
import com.ericdream.erictv.data.model.network.ViuTvRequest
import com.ericdream.erictv.data.repo.interfaces.LiveLinkGenerator
import javax.inject.Inject

class LiveLinkGeneratorImpl @Inject constructor(private val nowApi: NowApi) : LiveLinkGenerator {

    override suspend fun getNow331Link(): ChannelResult {
        return try {
            val bo = nowApi.getNowTV(channelno = "331")
            val link = bo.asset.hls.adaptive[0]
            Log.d(TAG, "getNow331Link: $link")
            ChannelResult.create(link)
        } catch (e: Exception) {
            ChannelResult.create(e)
        }
    }

    override suspend fun getNow332Link(): ChannelResult {
        return try {
            val bo = nowApi.getNowTV(channelno = "332")
            val link = bo.asset.hls.adaptive[0]
            Log.d(TAG, "getNow332Link: $link")
            ChannelResult.create(link)
        } catch (e: Exception) {
            ChannelResult.create(e)
        }
    }

    override suspend fun getViuTVLink(): ChannelResult {
        val request = ViuTvRequest(
            channelno = "099",
            deviceId = "99999999"
        )
        return try {
            val bo = nowApi.getViuTV(request)
            val link = bo.asset.hls.adaptive[0]
            ChannelResult.create(link)
        } catch (e: Exception) {
            ChannelResult.create(e)
        }
    }

    companion object {
        private const val TAG = "eric1999"
    }
}