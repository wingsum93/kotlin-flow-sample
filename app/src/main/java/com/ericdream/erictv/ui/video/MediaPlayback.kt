package com.ericdream.erictv.ui.video

interface MediaPlayback {
    fun playPause()

    fun forward(durationInMillis: Long)

    fun rewind(durationInMillis: Long)
}