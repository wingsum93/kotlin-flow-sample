package com.ericdream.erictv.ui.playvideo

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.ericdream.erictv.App
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.DebugTextViewHelper
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.util.Util
import com.google.gson.Gson
import timber.log.Timber

class VideoViewModel(app: Application) : AndroidViewModel(app), Player.EventListener {


    // For Video Player
    lateinit var player: Player
    private var mediaSource: MediaSource? = null
    private var trackSelector: DefaultTrackSelector? = null
    private var trackSelectorParameters: DefaultTrackSelector.Parameters? = null
    private var debugViewHelper: DebugTextViewHelper? = null
    private var lastSeenTrackGroupArray: TrackGroupArray? = null
    private var dataSourceFactory: DataSource.Factory? = null

    private var startAutoPlay: Boolean = false
    private var startWindow: Int = 0
    private var startPosition: Long = 0

    private val gson = Gson()
    private var lowQuality: Boolean = true

    fun cra() {
        player.addListener(this)
        trackSelector = DefaultTrackSelector()
        trackSelectorParameters = createTrackSelectorParameter()
    }

    private fun createTrackSelectorParameter(): DefaultTrackSelector.Parameters {
        return DefaultTrackSelector.ParametersBuilder()
            .setForceLowestBitrate(true)
            .build()
    }

    fun playVideo(uri: Uri) {

    }

    override fun onLoadingChanged(isLoading: Boolean) {
        super.onLoadingChanged(isLoading)
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
        super.onPlaybackParametersChanged(playbackParameters)
    }

    override fun onSeekProcessed() {
        super.onSeekProcessed()
    }

    override fun onTracksChanged(
        trackGroups: TrackGroupArray?,
        trackSelections: TrackSelectionArray?
    ) {
        Timber.d(gson.toJson(trackGroups))
        super.onTracksChanged(trackGroups, trackSelections)
    }

    override fun onPlayerError(error: ExoPlaybackException?) {
        super.onPlayerError(error)
    }

    /**
     * Returns a new DataSource factory.
     */
    private fun buildDataSourceFactory(): DataSource.Factory {
        return getApplication<App>().buildDataSourceFactory()
    }

    private fun buildMediaSource(uri: Uri, overrideExtension: String?): MediaSource {

        @com.google.android.exoplayer2.C.ContentType val type =
            Util.inferContentType(uri, overrideExtension)
        return when (type) {
            com.google.android.exoplayer2.C.TYPE_DASH -> DashMediaSource.Factory(
                dataSourceFactory
            ).createMediaSource(uri)
            com.google.android.exoplayer2.C.TYPE_SS -> SsMediaSource.Factory(
                dataSourceFactory
            ).createMediaSource(uri)
            com.google.android.exoplayer2.C.TYPE_HLS -> HlsMediaSource.Factory(
                dataSourceFactory
            ).createMediaSource(uri)
            com.google.android.exoplayer2.C.TYPE_OTHER -> ProgressiveMediaSource.Factory(
                dataSourceFactory
            ).createMediaSource(uri)
            else -> throw IllegalStateException("Unsupported type: $type")
        }
    }
}