package com.ericdream.erictv.ui.home

import android.net.Uri
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import com.ericdream.erictv.MediaPlayback
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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun VideoScreen(
    link: String,
    onPlayPause: () -> Unit = {},
    onForward: (Long) -> Unit = {},
    onRewind: (Long) -> Unit = {}
) {
    // This is the official way to access current context from Composable functions
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Timber.i("link: $link")
    val dataSourceFactory: DataSource.Factory = remember {
        DefaultDataSourceFactory(context, "eric-tv")
    }
    val trackSelectorParameter = remember(context) {
        DefaultTrackSelector.ParametersBuilder(context)
            .setForceLowestBitrate(true)
            .build()
    }
    val trackSelector = remember(context) {
        DefaultTrackSelector(context).also { it.parameters = trackSelectorParameter }
    }
    // Do not recreate the player everytime this Composable commits
    val exoPlayer = remember(context) {
        SimpleExoPlayer.Builder(context)
            .setTrackSelector(trackSelector)
            .build()
    }
    LaunchedEffect(key1 = link) {
        val source = dataSourceFactory.buildMediaSource(Uri.parse(link), null)
        exoPlayer.setMediaSource(source)
        exoPlayer.prepare()
    }
    val mediaPlayback = remember(exoPlayer) {
        object : MediaPlayback {
            override fun playPause() {
                exoPlayer.playWhenReady = !exoPlayer.playWhenReady
                onPlayPause.invoke()
            }

            override fun forward(durationInMillis: Long) {
                exoPlayer.seekTo(exoPlayer.currentPosition + durationInMillis)
                onForward.invoke(durationInMillis)
            }

            override fun rewind(durationInMillis: Long) {
                exoPlayer.seekTo(exoPlayer.currentPosition - durationInMillis)
                onRewind.invoke(durationInMillis)
            }
        }
    }
    // Gateway to legacy Android Views through XML inflation.
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f, true)
    ) {
        val (videoView, controllerView) = createRefs()
        var showController by remember {
            mutableStateOf(false)
        }
        var latestInteractionTimeMill by remember {
            mutableStateOf(System.currentTimeMillis())
        }
        val controllerTransition =
            updateTransition(targetState = showController, label = "updateTransition")
        val controllerColor = Color.White
        AndroidView({
            StyledPlayerView(it).apply {
                player = exoPlayer
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                useController = false
            }
        }, modifier = Modifier
            .constrainAs(videoView) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .clickable {
                showController = true
                latestInteractionTimeMill = System.currentTimeMillis()
            }
            .aspectRatio(16f / 9f, true))
        val controllerIconSize = DpSize(50.dp, 50.dp)
        controllerTransition.AnimatedVisibility(
            visible = { it },
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
        ) {
            val ccc = Color(0x2E, 0x2E, 0x2E, 0xB5)
            Surface(
                color = ccc,
                modifier = Modifier.fillMaxSize()
            ) {
                Box {
                    Row(
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        IconButton(onClick = {
                            mediaPlayback.rewind(10_000)
                            latestInteractionTimeMill = System.currentTimeMillis()
                        }) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                null,
                                tint = controllerColor,
                                modifier = Modifier.size(controllerIconSize)
                            )
                        }

                        IconButton(onClick = {
                            mediaPlayback.playPause()
                            latestInteractionTimeMill = System.currentTimeMillis()
                        }) {
                            Icon(
                                Icons.Filled.PlayArrow,
                                null,
                                tint = controllerColor,
                                modifier = Modifier.size(controllerIconSize)
                            )
                        }

                        IconButton(onClick = {
                            mediaPlayback.forward(10_000)
                            latestInteractionTimeMill = System.currentTimeMillis()
                        }) {
                            Icon(
                                Icons.Filled.ArrowForward,
                                null,
                                tint = controllerColor,
                                modifier = Modifier.size(controllerIconSize)
                            )
                        }
                    }
                }
            }
        }
        val handler = CoroutineExceptionHandler { _, exception ->
            Timber.e("CoroutineExceptionHandler got $exception")
        }
        produceState(initialValue = showController, key1 = latestInteractionTimeMill) {
            val job = scope.launch(handler) {
                Timber.i("LaunchedEffect is called xz")
                delay(3000L)
                this@produceState.value = false
                showController = false
            }
            awaitDispose {
                job.cancel()
            }
        }
    }
    DisposableEffect(key1 = true) {
        exoPlayer.playWhenReady = true
        onDispose {
            exoPlayer.pause()
            exoPlayer.release()
        }
    }
}

fun shouldHideController(latestInteractionTimeMill: Long): Boolean {
    val now = System.currentTimeMillis()
    Timber.i("cal shouldHideController = $latestInteractionTimeMill")
    return ((now - latestInteractionTimeMill) > 3000L)
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

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Preview
@Composable
fun VideoScreenPreview() {
    VideoScreen("https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8")
}