package com.ericdream.erictv.utils

object PlaybackStateDecoder {

    fun decodePlaybackState(playbackState: Int): String {
        return when (playbackState) {
            1 -> "STATE_IDLE"
            2 -> "STATE_BUFFERING"
            3 -> "STATE_READY"
            4 -> "STATE_ENDED"
            else -> ""
        }
    }

}