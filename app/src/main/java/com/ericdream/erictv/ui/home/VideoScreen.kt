package com.ericdream.erictv.ui.home

import android.net.Uri
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import timber.log.Timber

@Composable
fun VideoScreen(link: String) {
    // This is the official way to access current context from Composable functions
    val context = LocalContext.current
    Timber.i("link: $link")
    val dataSourceFactory: DataSource.Factory = remember {
        DefaultDataSourceFactory(context, "eric-tv")
    }
    val trackSelectorParameter = remember {
        DefaultTrackSelector.ParametersBuilder(context)
            .setForceLowestBitrate(true)
            .build()
    }
    val trackSelector = remember {
        DefaultTrackSelector(context).also { it.parameters = trackSelectorParameter }
    }
    // Do not recreate the player everytime this Composable commits
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context)
            .setTrackSelector(trackSelector)
            .build()
    }
    LaunchedEffect(key1 = link) {
        val source = dataSourceFactory.buildMediaSource(Uri.parse(link), null)
        exoPlayer.setMediaSource(source)
        exoPlayer.prepare()
    }
    // Gateway to legacy Android Views through XML inflation.
    AndroidView({
        StyledPlayerView(it).apply {
            player = exoPlayer
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }, modifier = Modifier)

    DisposableEffect(key1 = true) {
        exoPlayer.playWhenReady = true
        onDispose {
            exoPlayer.pause()
            exoPlayer.release()
        }
    }
}

private fun DataSource.Factory.buildMediaSource(uri: Uri, overrideExtension: String?): MediaSource {

    @com.google.android.exoplayer2.C.ContentType val type =
        Util.inferContentType(uri, overrideExtension)
    val item = MediaItem.fromUri(uri)
    return when (type) {
        com.google.android.exoplayer2.C.TYPE_DASH -> DashMediaSource.Factory(
            this
        ).createMediaSource(item)
        com.google.android.exoplayer2.C.TYPE_SS -> SsMediaSource.Factory(
            this
        ).createMediaSource(item)
        com.google.android.exoplayer2.C.TYPE_HLS -> HlsMediaSource.Factory(
            this
        ).createMediaSource(item)
        com.google.android.exoplayer2.C.TYPE_OTHER -> ProgressiveMediaSource.Factory(
            this
        ).createMediaSource(item)
        else -> throw IllegalStateException("Unsupported type: $type")
    }
}