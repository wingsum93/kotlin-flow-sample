package com.ericdream.erictv.ui

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ericdream.erictv.App
import com.ericdream.erictv.C
import com.ericdream.erictv.R
import com.ericdream.erictv.databinding.ActPlayVideoBinding
import com.ericdream.erictv.ui.playvideo.VideoViewModel
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.DebugTextViewHelper
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.util.Util
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayVideoAct : AppCompatActivity() {


    lateinit var viewDataBinding: ActPlayVideoBinding
    private val viewModel by viewModel<VideoViewModel>()

    // For Video Player
    private var player: SimpleExoPlayer? = null
    private var mediaSource: MediaSource? = null
    private var trackSelector: DefaultTrackSelector? = null
    private var trackSelectorParameters: DefaultTrackSelector.Parameters? = null
    private var debugViewHelper: DebugTextViewHelper? = null
    private var lastSeenTrackGroupArray: TrackGroupArray? = null
    private var dataSourceFactory: DataSource.Factory? = null

    private var startAutoPlay: Boolean = false
    private var startWindow: Int = 0
    private var startPosition: Long = 0

    private lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.act_play_video)

        viewDataBinding.lifecycleOwner = this
        viewDataBinding.vm = viewModel
        //get data

        uri = intent.getParcelableExtra(C.Key.URI) as Uri
        viewDataBinding.playerView


        trackSelector = DefaultTrackSelector()
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
        viewDataBinding.playerView?.player = player
        //
        dataSourceFactory = buildDataSourceFactory()
        mediaSource = buildMediaSource(uri, null)

        player?.playWhenReady = true

        player?.prepare(mediaSource)


    }

    private fun buildMediaSource(uri: Uri, overrideExtension: String?): MediaSource {

        @com.google.android.exoplayer2.C.ContentType val type =
            Util.inferContentType(uri, overrideExtension)
        when (type) {
            com.google.android.exoplayer2.C.TYPE_DASH -> return DashMediaSource.Factory(
                dataSourceFactory
            ).createMediaSource(uri)
            com.google.android.exoplayer2.C.TYPE_SS -> return SsMediaSource.Factory(
                dataSourceFactory
            ).createMediaSource(uri)
            com.google.android.exoplayer2.C.TYPE_HLS -> return HlsMediaSource.Factory(
                dataSourceFactory
            ).createMediaSource(uri)
            com.google.android.exoplayer2.C.TYPE_OTHER -> return ProgressiveMediaSource.Factory(
                dataSourceFactory
            ).createMediaSource(uri)
            else -> throw IllegalStateException("Unsupported type: $type")
        }
    }

    /**
     * Returns a new DataSource factory.
     */
    private fun buildDataSourceFactory(): DataSource.Factory {
        return (application as App).buildDataSourceFactory()
    }


    override fun onDestroy() {
        player?.release()
        super.onDestroy()
    }
}