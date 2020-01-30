package com.ericdream.erictv

object C {

    const val APP_NAME: String = "ERIC_TV"
    const val KEY_USER_SETTING: String = "KEY_USER_SETTING"
    const val SAMPLE_HLS_LINK = "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8"
    const val RTHK_31_LINK =
        "https://rthklive1-lh.akamaihd.net/i/rthk31_1@167495/index_2052_av-b.m3u8"
    const val RTHK_32_LINK =
        "https://rthklive2-lh.akamaihd.net/i/rthk32_1@168450/index_162_av-p.m3u8"
    const val OPENTV_LINK = "http://media.fantv.hk/m3u8/archive/channel2_stream1.m3u8"
    const val VIUTV_ICON_LINK =
        "https://yt3.ggpht.com/a/AGF-l7_fMTospARmgQ60hAyJIYqKer7799fcfgDt=s288-c-k-c0xffffffff-no-rj-mo"
    const val LOCAL_HLS_H_LINK =
        "http://192.168.1.100:8080/hls/%e5%ae%b6%e6%9c%89%e5%96%9c%e4%ba%8b3/index.m3u8"
    const val NETWORK_HLS_LINK =
        "https://s3.ap-northeast-1.amazonaws.com/www.eric-dream.com/streaming/%E5%B0%8F%E7%94%B7%E4%BA%BA%E5%91%A8%E8%A8%98%E4%B9%8B%E5%90%BE%E5%AE%B6%E6%9C%89%E5%96%9C/index.m3u8"

    object Icon {
        const val VIUTV =
            "https://thematrix.dev/tvhk/viutv.jpg"
        const val NOWTV =
            "https://thematrix.dev/tvhk/nowtv.jpg"
        const val OPENTV =
            "https://thematrix.dev/tvhk/opentv.jpg"
        const val CABLETV =
            "https://thematrix.dev/tvhk/cabletv.jpg"
        const val RTHK1 =
            "https://thematrix.dev/tvhk/rthktv31.jpg"
        const val RTHK2 =
            "https://thematrix.dev/tvhk/rthktv32.jpg"
    }


    object Key {
        const val URI = "URI"
        const val LIVECHANNEL = "LIVECHANNEL"
    }
}