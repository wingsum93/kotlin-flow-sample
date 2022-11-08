package com.ericdream.erictv.data.model.network

data class ViuTvRequest(
    //  ViuTV
    var channelno: String = "099",
    var deviceId: String = "",
    var deviceType: String = "5",
    var callerReferenceNo: String = "",
    var format: String = "HLS",
    var mode: String = "prod"
)