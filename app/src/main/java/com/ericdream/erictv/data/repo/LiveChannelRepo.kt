package com.ericdream.erictv.data.repo

import com.ericdream.erictv.C
import com.ericdream.erictv.data.model.LiveChannel

class LiveChannelRepo() {

    private val livechannels = mutableListOf<LiveChannel>()

    fun getLiveChannels(): List<LiveChannel> {
        livechannels += (LiveChannel.create("Open TV", C.Icon.OPENTV, C.OPENTV_LINK))
        livechannels += (LiveChannel.create("RTHK 1", C.Icon.RTHK1, C.RTHK_31_LINK))
        livechannels += (LiveChannel.create("RTHK 2", C.Icon.RTHK2, C.RTHK_32_LINK))
        livechannels += (LiveChannel.create("ViuTv", C.Icon.VIUTV, null))

        return livechannels.toList()
    }
}