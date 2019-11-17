package com.ericdream.erictv.data.repo

import com.ericdream.erictv.C
import com.ericdream.erictv.data.model.ChannelResult
import com.ericdream.erictv.data.model.LiveChannel

class LiveChannelRepo() {

    private val livechannels = mutableListOf<LiveChannel>()
    private val generater: LiveLinkGenerater = LiveLinkGenerater()
    init {
        livechannels += (LiveChannel.create("Open TV", C.Icon.OPENTV, C.OPENTV_LINK))
        livechannels += (LiveChannel.create("RTHK 1", C.Icon.RTHK1, C.RTHK_31_LINK))
        livechannels += (LiveChannel.create("RTHK 2", C.Icon.RTHK2, C.RTHK_32_LINK))
        livechannels += (LiveChannel.create("ViuTv", C.Icon.VIUTV, null))
    }


    fun getLiveChannels(): List<LiveChannel> {
        return livechannels.toList()
    }

    suspend fun getLink(key: String): ChannelResult {

        return when (key) {
            "331" -> generater.getNow331Link()
            "332" -> generater.getNow332Link()
            "viutv" -> generater.getViuTVLink()
            else -> throw IllegalArgumentException()
        }
    }
}