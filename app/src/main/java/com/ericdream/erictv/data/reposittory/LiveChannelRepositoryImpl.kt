package com.ericdream.erictv.data.reposittory

import com.ericdream.erictv.data.model.ChannelResult
import com.ericdream.erictv.data.model.LiveChannel
import com.ericdream.erictv.data.reposittory.interfaces.LiveChannelRepository
import com.ericdream.erictv.data.reposittory.interfaces.LiveLinkGenerator
import com.ericdream.erictv.utils.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LiveChannelRepositoryImpl @Inject constructor(private val liveLinkGenerator: LiveLinkGenerator) :
    LiveChannelRepository {

    private val livechannels = mutableListOf<LiveChannel>()

    init {
        livechannels += LiveChannel.liveChannel {
            name = NAME_OPENTV
            iconLink = Constant.ICON_OPENTV
            link = Constant.STREAMING_LINK_OPENTV_LINK
            key = Constant.KEY_OPENTV
        }
        livechannels += LiveChannel.liveChannel {
            name = NAME_RTHK1
            iconLink = Constant.ICON_RTHK1
            link = Constant.STREAMING_LINK_RTHK_31
            key = Constant.KEY_RTHK1
        }
        livechannels += LiveChannel.liveChannel {
            name = NAME_RTHK2
            iconLink = Constant.ICON_RTHK2
            link = Constant.STREAMING_LINK_RTHK_32
            key = Constant.KEY_RTHK2
        }
        livechannels += LiveChannel.liveChannel {
            name = NAME_331
            iconLink = Constant.ICON_NOWTV
            key = Constant.KEY_331
        }
        livechannels += LiveChannel.liveChannel {
            name = NAME_332
            iconLink = Constant.ICON_NOWTV
            key = Constant.KEY_332
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
            Constant.KEY_331 -> liveLinkGenerator.getNow331Link()
            Constant.KEY_332 -> liveLinkGenerator.getNow332Link()
            Constant.KEY_VIUTV -> liveLinkGenerator.getViuTVLink()
            else -> ChannelResult.create(IllegalArgumentException())
        }
    }

    companion object {
        private const val NAME_331 = "Now 331"
        private const val NAME_332 = "Now 332"
        private const val NAME_RTHK1 = "RTHK 1"
        private const val NAME_RTHK2 = "RTHK 2"
        private const val NAME_OPENTV = "Open TV"
    }
}