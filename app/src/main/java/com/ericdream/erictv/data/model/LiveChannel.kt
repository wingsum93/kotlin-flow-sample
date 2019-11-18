package com.ericdream.erictv.data.model

import java.io.Serializable

class LiveChannel : Serializable {
    var name: String = ""
    var iconLink: String = ""
    var link: String? = null
    // key for identify channel
    var key: String = ""


    companion object {

        fun create(name: String = "", iconLink: String, link: String?): LiveChannel {
            return LiveChannel().apply {
                this.name = name
                this.link = link
                this.iconLink = iconLink
            }

        }

        fun liveChannel(block: LiveChannel.() -> Unit): LiveChannel {
            val o = LiveChannel()
            block.invoke(o)
            return o
        }
    }
}