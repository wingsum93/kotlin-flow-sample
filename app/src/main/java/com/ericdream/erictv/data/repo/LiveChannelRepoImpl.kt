package com.ericdream.erictv.data.repo

import com.ericdream.erictv.C
import com.ericdream.erictv.data.model.ChannelResult
import com.ericdream.erictv.data.model.LiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LiveChannelRepoImpl @Inject constructor(private val generator: LiveLinkGenerator) :
    LiveChannelRepo {

    private val livechannels = mutableListOf<LiveChannel>()

    init {

        livechannels += LiveChannel.liveChannel {
            name = "Open TV"
            iconLink = C.Icon.OPENTV
            link = C.OPENTV_LINK
            key = "opentv"
        }
        livechannels += LiveChannel.liveChannel {
            name = "RTHK 1"
            iconLink = C.Icon.RTHK1
            link = C.RTHK_31_LINK
            key = "rthk1"
        }
        livechannels += LiveChannel.liveChannel {
            name = "RTHK 2"
            iconLink = C.Icon.RTHK2
            link = C.RTHK_32_LINK
            key = "rthk2"
        }
        livechannels += LiveChannel.liveChannel {
            name = "ViuTv"
            iconLink = C.Icon.VIUTV

            key = "viutv"
        }
        livechannels += LiveChannel.liveChannel {
            name = "Now 331"
            iconLink = C.Icon.NOWTV

            key = "331"
        }
        livechannels += LiveChannel.liveChannel {
            name = "Now 332"
            iconLink = C.Icon.NOWTV

            key = "332"
        }
        livechannels += LiveChannel.liveChannel {
            name = "CABLE TV (MOCK)"
            iconLink = C.Icon.CABLETV
            link = C.NETWORK_HLS_LINK
            key = "xxxtv"
        }
    }

    override fun getLiveChannels(): Flow<List<LiveChannel>> {
        return flowOf(livechannels.toList())
    }

    override suspend fun getLink(key: String): Flow<ChannelResult> {
        return when (key) {
            "331" -> generator.getNow331Link()
            "332" -> generator.getNow332Link()
            "viutv" -> generator.getViuTVLink()
            else -> flowOf(ChannelResult.create(IllegalArgumentException()))
        }
    }
}