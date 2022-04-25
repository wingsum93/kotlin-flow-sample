package com.ericdream.erictv

interface MediaPlayback {
    fun playPause()

    fun forward(durationInMillis: Long)

    fun rewind(durationInMillis: Long)
}