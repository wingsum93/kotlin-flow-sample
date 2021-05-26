package com.ericdream.erictv.util

import java.net.URLEncoder
import kotlin.random.Random
import kotlin.random.nextInt

class VideoLinkGenerator(val videoDomain: String = "video.eric-dream.com") {

    private var videoSuffixSet: MutableSet<String> = mutableSetOf()
    private var videoSuffixList: MutableList<String> = mutableListOf()

    val videoSuffix_AA =
        "%E5%B0%8F%E7%94%B7%E4%BA%BA%E5%91%A8%E8%A8%98%E4%B9%8B%E5%90%BE%E5%AE%B6%E6%9C%89%E5%96%9C/index.m3u8"
    val videoSuffix_adult_sample = "test-adult-video/sample.m3u8"

    //ipx201- 10 second segment
    val ipx201_10 = "ipx201-10s/index.m3u8"

    //ipx201 - 30s segment
    val ipx201_30 = "ipx201-a/index.m3u8"

    //bagbd068
    val bagbd068 = "bagbd068-aa/bagbd_068-aa.m3u8"

    init {
        videoSuffixList.add(videoSuffix_AA)
        videoSuffixList.add(videoSuffix_adult_sample)
        videoSuffixList.add(ipx201_10)
        videoSuffixList.add(ipx201_30)
        videoSuffixList.add(bagbd068)
        videoSuffixList.shuffle()
    }

    fun getRandomLink(): String {
        val a = Random.nextInt(0 until videoSuffixList.size)
        val key = videoSuffixList[a]
        return String.format(videoLinkFormat, videoDomain, key)
    }

    fun getFullVideoList(): List<String> {
        return videoSuffixList.map { String.format(videoLinkFormat, videoDomain, it) }
    }


    companion object {
        const val separator = "/"
        const val videoLinkFormat = "https://%s/%s"
    }

    fun String.toUrlEncoded(): String = URLEncoder.encode(this, "utf-8")

}