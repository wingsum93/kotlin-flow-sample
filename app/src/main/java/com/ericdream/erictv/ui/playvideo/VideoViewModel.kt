package com.ericdream.erictv.ui.playvideo

import android.app.Application
import android.net.Uri
import android.view.View
import androidx.lifecycle.*
import com.ericdream.erictv.App
import com.ericdream.erictv.data.model.LiveChannel
import com.google.android.exoplayer2.*
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

class VideoViewModel(app: Application) : AndroidViewModel(app), Player.EventListener,
    LifecycleObserver {


    // For Video Player
    val _player: MutableLiveData<ExoPlayer?> = MutableLiveData()
    val player: LiveData<ExoPlayer?>
        get() = _player

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
    private var init: Boolean = false
    /**
     * Indicate the play state of video
     */
    val videoPlay: MutableLiveData<Boolean> = MutableLiveData(false)
    val videoLoading: MutableLiveData<Boolean> = MutableLiveData(false)


    val PLAY_ICON_RES = com.ericdream.erictv.R.drawable.ic_play_circle_outline_white_24dp
    val PAUSE_ICON_RES = com.ericdream.erictv.R.drawable.ic_pause_circle_outline_white_24dp
    val playIconRes: LiveData<Int>
    val playIconRes2: MutableLiveData<Int> = MutableLiveData(PLAY_ICON_RES)

    init {
        playIconRes = Transformations.map(videoPlay) { input ->
            when (input) {
                true -> return@map PAUSE_ICON_RES
                else -> return@map PLAY_ICON_RES
            }

        }
    }

    fun init(uri: Uri, liveChannel: LiveChannel) {
        if (!init) {
            Timber.d("HIHI UYI!!")
            init = true
            initImplementation(uri)

        }

    }

    fun initImplementation(uri: Uri) {

        dataSourceFactory = buildDataSourceFactory()

        trackSelectorParameters = createTrackSelectorParameter()

        mediaSource = buildMediaSource(uri, null)

    }


    fun playIconClick(view: View) {
        playOrPause()
    }

    fun volumeIconClick(view: View) {
        player.value?.let {
            val a = it as SimpleExoPlayer
            //
            val volume = a.volume
            if (volume > 0f) {
                //mute it
                a.volume = 0f
            } else {
                a.volume = 1f
            }
        }
    }

    fun fullscreenIconClick(view: View) {
        //todo
    }

    private fun playOrPause() {
        player.value?.let {
            it.playWhenReady = !it.playWhenReady
        }
    }


    fun setUpPlayer() {
        trackSelector = DefaultTrackSelector()

        _player.value = ExoPlayerFactory.newSimpleInstance(getApplication(), trackSelector)

        player.value?.let {
            it.addListener(this)
            it.prepare(mediaSource)
        }


        Timber.i("SetUp PLayer")
    }


    fun releasePlayer() {
        Timber.i("Release PLayer")
        _player.value?.let {
            it.playWhenReady = false
            it.release()
        }

        _player.postValue(null)
    }

    private fun createTrackSelectorParameter(): DefaultTrackSelector.Parameters {
        return DefaultTrackSelector.ParametersBuilder()
            .setForceLowestBitrate(true)
            .build()
    }


    override fun onLoadingChanged(isLoading: Boolean) {
        videoLoading.postValue(isLoading)
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
        super.onPlaybackParametersChanged(playbackParameters)
    }

    override fun onSeekProcessed() {
        super.onSeekProcessed()
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        videoPlay.value = (playWhenReady)
        Timber.d("playWhenReady = $playWhenReady, playbackState = $playbackState")


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

    override fun onCleared() {
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
        super.onCleared()
    }
}