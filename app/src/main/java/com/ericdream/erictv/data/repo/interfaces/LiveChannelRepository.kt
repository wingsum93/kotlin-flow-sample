package com.ericdream.erictv.data.repo.interfaces

import com.ericdream.erictv.data.model.ChannelResult
import com.ericdream.erictv.data.model.LiveChannel
import kotlinx.coroutines.flow.Flow

interface LiveChannelRepository {
    fun getLiveChannels(): Flow<List<LiveChannel>>
    suspend fun getLink(key: String): ChannelResult
}
