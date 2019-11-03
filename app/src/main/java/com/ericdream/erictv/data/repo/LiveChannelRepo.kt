package com.ericdream.erictv.data.repo

import com.ericdream.erictv.C
import com.ericdream.erictv.data.model.LiveChannel
import java.util.*

class LiveChannelRepo() {


    fun getLiveChannels(): List<LiveChannel> {
        return Collections.singletonList(LiveChannel.create("ViuTv99", C.OPENTV_LINK))
    }
}