package com.ericdream.erictv.data.repo

import com.ericdream.erictv.data.model.ChannelResult
import kotlinx.coroutines.flow.Flow

interface LiveLinkGenerator {
    suspend fun getNow331Link(): Flow<ChannelResult>
    suspend fun getNow332Link(): Flow<ChannelResult>
    suspend fun getViuTVLink(): Flow<ChannelResult>
}
