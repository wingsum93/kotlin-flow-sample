package com.ericdream.erictv

import com.ericdream.erictv.data.repo.LiveLinkGenerater
import com.ericdream.erictv.ui.home.MainViewModel
import com.ericdream.erictv.ui.playvideo.VideoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // View models
    viewModel { MainViewModel() }

    viewModel { VideoViewModel(get()) }


    factory { LiveLinkGenerater() }

}