package com.ericdream.erictv

object C {

    const val APP_NAME: String = "ERIC_TV"
    const val KEY_USER_SETTING: String = "KEY_USER_SETTING"
    const val SAMPLE_HLS_LINK = "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8"
    const val RTHK_31_LINK =
        "https://www.rthk.hk/feeds/dtt/rthktv31_https.m3u8?v=20200211"
    const val RTHK_32_LINK =
        "https://www.rthk.hk/feeds/dtt/rthktv32_https.m3u8?v=20200211"
    const val OPENTV_LINK = "http://media.fantv.hk/m3u8/archive/channel2_stream1.m3u8"
    const val VIUTV_ICON_LINK =
        "https://yt3.ggpht.com/a/AGF-l7_fMTospARmgQ60hAyJIYqKer7799fcfgDt=s288-c-k-c0xffffffff-no-rj-mo"
    const val LOCAL_HLS_H_LINK =
        "http://192.168.1.100:8080/hls/%e5%ae%b6%e6%9c%89%e5%96%9c%e4%ba%8b3/index.m3u8"
    const val NETWORK_HLS_LINK =
        "https://s3.ap-northeast-1.amazonaws.com/www.eric-dream.com/streaming/%E5%B0%8F%E7%94%B7%E4%BA%BA%E5%91%A8%E8%A8%98%E4%B9%8B%E5%90%BE%E5%AE%B6%E6%9C%89%E5%96%9C/index.m3u8"

    object Icon {
        const val VIUTV =
            "https://i.ibb.co/jT88Vyw/viutv-icon.png"
        const val NOWTV =
            "https://i.ibb.co/PzfwBwv/nowtv.jpg"
        const val OPENTV =
            "https://i.ibb.co/Rj3Zd7G/opentv.png"
        const val CABLETV =
            "https://i.ibb.co/tmCGZND/icable-1-300x300.png"
        const val RTHK1 =
            "https://i.ibb.co/3snzqxN/rthktv-live.jpg"
        const val RTHK2 =
            "https://i.ibb.co/z2t4J3G/rthk32.png"
    }

    object Data {
        const val video_cdn_1 = "d25hl4oq2vxbl1.cloudfront.net"
        const val video_cdn_2 = "video.eric-dream.com"
    }

    object Key {
        const val URI = "URI"
        const val LIVECHANNEL = "LIVECHANNEL"
    }
}