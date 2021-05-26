package com.ericdream.erictv.data.repo

import com.ericdream.erictv.C
import com.ericdream.erictv.data.model.ChannelResult
import com.ericdream.erictv.data.model.LiveChannel

class LiveChannelRepo constructor(private val generater: LiveLinkGenerater) {

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
            iconLink = C.Icon.VIUTV

            key = "331"
        }
        livechannels += LiveChannel.liveChannel {
            name = "Now 332"
            iconLink = C.Icon.VIUTV

            key = "332"
        }
        livechannels += LiveChannel.liveChannel {
            name = "XXX TV"
            iconLink = C.Icon.OPENTV
            link = C.NETWORK_HLS_LINK
            key = "xxxtv"
        }
    }


    fun getLiveChannels(): List<LiveChannel> {
        return livechannels.toList()
    }

    suspend fun getLink(key: String): ChannelResult {

        return when (key) {
            "331" -> generater.getNow331Link()
            "332" -> generater.getNow332Link()
            "viutv" -> generater.getViuTVLink()
            else -> ChannelResult.create(IllegalArgumentException())
        }
    }
}