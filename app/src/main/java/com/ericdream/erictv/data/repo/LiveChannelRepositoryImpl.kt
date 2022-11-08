package com.ericdream.erictv.data.repo

import com.ericdream.erictv.data.model.ChannelResult
import com.ericdream.erictv.data.model.LiveChannel
import com.ericdream.erictv.data.repo.interfaces.LiveChannelRepository
import com.ericdream.erictv.data.repo.interfaces.LiveLinkGenerator
import com.ericdream.erictv.utils.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LiveChannelRepositoryImpl @Inject constructor(private val liveLinkGenerator: LiveLinkGenerator) :
    LiveChannelRepository {

    private val livechannels = mutableListOf<LiveChannel>()

    init {
        livechannels += LiveChannel.liveChannel {
            name = "Open TV"
            iconLink = Constant.Icon.OPENTV
            link = Constant.OPENTV_LINK
            key = "opentv"
        }
        livechannels += LiveChannel.liveChannel {
            name = "RTHK 1"
            iconLink = Constant.Icon.RTHK1
            link = Constant.RTHK_31_LINK
            key = "rthk1"
        }
        livechannels += LiveChannel.liveChannel {
            name = "RTHK 2"
            iconLink = Constant.Icon.RTHK2
            link = Constant.RTHK_32_LINK
            key = "rthk2"
        }
        livechannels += LiveChannel.liveChannel {
            name = "Now 331"
            iconLink = Constant.Icon.NOWTV
            key = "331"
        }
        livechannels += LiveChannel.liveChannel {
            name = "Now 332"
            iconLink = Constant.Icon.NOWTV
            key = "332"
        }
    }

    override fun getLiveChannels(): Flow<List<LiveChannel>> = flow {
        for (i in livechannels) {
            if (i.link == null) {
                val result = getLink(i.key)
                if (result.link != null)
                    i.link = result.link
            }
        }
        emit(livechannels.toList())
    }

    override suspend fun getLink(key: String): ChannelResult {
        return when (key) {
            "331" -> liveLinkGenerator.getNow331Link()
            "332" -> liveLinkGenerator.getNow332Link()
            "viutv" -> liveLinkGenerator.getViuTVLink()
            else -> ChannelResult.create(IllegalArgumentException())
        }
    }
}