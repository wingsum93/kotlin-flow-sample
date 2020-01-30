package com.ericdream.erictv.ui.playvideo

import android.app.Application
import android.net.Uri
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.Transformations
import com.ericdream.erictv.App
import com.ericdream.erictv.R
import com.ericdream.erictv.data.model.LiveChannel
import com.ericdream.erictv.data.repo.UserRepository
import com.ericdream.erictv.util.PlaybackStateDecoder
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioListener
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

/**
 * A Live video view model
 */
class VideoViewModel(app: Application, val userRepository: UserRepository) : AndroidViewModel(app),
    Player.EventListener,
    LifecycleObserver,
    AudioListener {


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
    private var startWindowIndex: Int = 0
    private var startDuration: Long = 0

    private val gson = Gson()
    private var lowQuality: Boolean = true
    private var init: Boolean = false
    /**
     * Indicate the play state of video
     */
    val videoPlay: MutableLiveData<Boolean> = MutableLiveData(false)
    val videoLoading: MutableLiveData<Boolean> = MutableLiveData(false)


    val PLAY_ICON_RES = R.drawable.ic_play_circle_outline_white_24dp
    val PAUSE_ICON_RES = R.drawable.ic_pause_circle_outline_white_24dp
    val playIconRes: LiveData<Int>
    val volumeIconRes: MutableLiveData<Int> = MutableLiveData(R.drawable.ic_volume_up_white_24dp)

    private val userSettingIO = userRepository.getUserSetting()

    val showControllerLiveData: MutableLiveData<Boolean> = MutableLiveData(true)

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
            it.audioComponent?.addAudioListener(this)
            it.prepare(mediaSource, false, true)
            it.seekTo(startWindowIndex, startDuration)

            val sound = if (userSettingIO.defaultSound) 1f else 0f
            it.audioComponent?.volume = sound
        }


        Timber.i("SetUp PLayer")
    }


    fun releasePlayer() {
        Timber.i("Release PLayer")
        (_player.value as? SimpleExoPlayer)?.let {
            //save data
            startWindowIndex = it.currentWindowIndex
            startDuration = it.currentPosition
            it.currentTimeline
            it.audioComponent?.removeAudioListener(this)
            userSettingIO.defaultSound = (it.volume > 0f)
            userRepository.setUserSetting(userSettingIO)
            //
            it.release()
        }

        _player.postValue(null)
    }

    override fun onVolumeChanged(volume: Float) {
        val nextResInt =
            if (volume > 0) R.drawable.ic_volume_up_white_24dp else R.drawable.ic_volume_off_white_24dp
        volumeIconRes.value = nextResInt
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
        val playbackStateString: String = PlaybackStateDecoder.decodePlaybackState(playbackState)
        Timber.d("playWhenReady = $playWhenReady, playbackState = $playbackStateString")


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