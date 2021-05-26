package com.ericdream.erictv

import com.ericdream.erictv.util.VideoLinkGenerator
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class VideoLinkGenerationTest {

    val videoDomain: String = "d25hl4oq2vxbl1.cloudfront.net"

    lateinit var generator: VideoLinkGenerator

    @Before
    fun prepare() {
        generator = VideoLinkGenerator(C.Data.video_cdn_1)
    }

    @Test
    fun testRandomVideoLink() {
        for (i in 1 until 3) {
            println("Video Link $i ${generator.getRandomLink()}")
        }
    }

    @Test
    fun testFullListLink() {
        val list = generator.getFullVideoList()
        for ((index, item) in list.withIndex()) {
            println("Video Link $index $item")
        }
    }

}