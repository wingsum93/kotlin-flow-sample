package com.ericdream.erictv.data.model

import com.google.gson.annotations.SerializedName

/**
 * for now 331, now 332
 */
class NowBO {
    @SerializedName("responseCode")
    var responseCode: String = ""

    @SerializedName("serviceName")
    var serviceName: String = ""

    @SerializedName("asset")
    var asset: Asset = Asset()

    class Asset {
        var hls: Hls = Hls()
    }

    class Hls {
        var adaptive: List<String> = listOf()
    }
}