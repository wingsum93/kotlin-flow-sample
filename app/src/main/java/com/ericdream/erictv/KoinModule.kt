package com.ericdream.erictv

import com.ericdream.erictv.data.repo.UserRepository
import com.ericdream.erictv.ui.playvideo.VideoViewModel
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // View models
//    viewModel { MainViewModel() }

    viewModel { VideoViewModel(get(), get()) }


//    single { LiveLinkGeneratorImpl() }
//    single { LiveChannelRepoImpl(get()) }
    single { UserRepository(get()) }
    //other
    single<DataSource.Factory> { DefaultDataSourceFactory(androidContext(), "eric-tv") }
    factory<TrackSelector> { DefaultTrackSelector(androidContext()).also { it.parameters = get() } }
    factory<SimpleExoPlayer> {
        SimpleExoPlayer.Builder(get())
            .build()
    }
    factory {
        DefaultTrackSelector.ParametersBuilder(androidContext())
            .setForceLowestBitrate(true)
            .build()
    }
}