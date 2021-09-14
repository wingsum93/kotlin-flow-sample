package com.ericdream.erictv.data.repo

import com.ericdream.erictv.data.model.ChannelResult
import com.ericdream.erictv.data.model.LiveChannel

interface LiveChannelRepo {
    fun getLiveChannels(): List<LiveChannel>
    suspend fun getLink(key: String): ChannelResult
}
