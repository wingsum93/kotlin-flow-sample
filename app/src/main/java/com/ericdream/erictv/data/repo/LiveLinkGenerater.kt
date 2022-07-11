package com.ericdream.erictv.data.repo

import com.ericdream.erictv.data.model.ChannelResult

interface LiveLinkGenerator {
    suspend fun getNow331Link(): ChannelResult
    suspend fun getNow332Link(): ChannelResult
    suspend fun getViuTVLink(): ChannelResult
}
