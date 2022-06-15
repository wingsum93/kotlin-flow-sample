package com.ericdream.erictv.data.repo

import com.ericdream.erictv.data.api.NowApi
import com.ericdream.erictv.data.model.ChannelResult
import com.ericdream.erictv.data.model.NowIO
import com.ericdream.erictv.data.model.ViuTvIO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LiveLinkGeneratorImpl @Inject constructor(val nowApi: NowApi) : LiveLinkGenerator {


    override suspend fun getNow331Link(): Flow<ChannelResult> = flow {
        val io = NowIO()
        io.channelno = "331"
        try {
            val bo = nowApi.getNowTV(io)
            val link = bo.asset.hls.adaptive[0]
            emit(ChannelResult.create(link))
        } catch (e: Exception) {
            emit(ChannelResult.create(e))
        }
    }

    override suspend fun getNow332Link(): Flow<ChannelResult> = flow {
        val io = NowIO()
        io.channelno = "332"
        try {
            val bo = nowApi.getNowTV(io)
            val link = bo.asset.hls.adaptive[0]
            emit(ChannelResult.create(link))
        } catch (e: Exception) {
            emit(ChannelResult.create(e))
        }
    }

    override suspend fun getViuTVLink(): Flow<ChannelResult> = flow {
        val io = ViuTvIO()
        io.channelno = "099"
        io.deviceId = "99999999"
        try {
            val bo = nowApi.getViuTV(io)
            val link = bo.asset.hls.adaptive[0]
            emit(ChannelResult.create(link))
        } catch (e: Exception) {
            emit(ChannelResult.create(e))
        }
    }
}