package com.ericdream.erictv.ui.playvideo

interface MediaPlayback {
    fun playPause()

    fun forward(durationInMillis: Long)

    fun rewind(durationInMillis: Long)
}