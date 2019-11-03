package com.ericdream.erictv.data.model

class LiveChannel {
    var name: String = ""
    var link: String? = null

    companion object {

        fun create(name: String = "", link: String?): LiveChannel {
            return LiveChannel().apply {
                this.name = name
                this.link = link
            }
        }
    }
}