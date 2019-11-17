package com.ericdream.erictv.data.model

class ChannelResult {
    var link: String? = null
    var exception: Exception? = null

    val error: Boolean
        get() = exception != null

    companion object {

        val SmapleError: ChannelResult = ChannelResult().apply {
            exception = IllegalAccessException()
        }

        fun create(link: String): ChannelResult = ChannelResult().apply {
            this.link = link
        }

        fun create(exception: Exception): ChannelResult = ChannelResult().apply {
            this.exception = exception
        }
    }
} 