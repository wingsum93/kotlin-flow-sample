package com.ericdream.erictv

import com.ericdream.erictv.data.repo.LiveChannelRepo
import com.ericdream.erictv.data.repo.LiveChannelRepoImpl
import com.ericdream.erictv.data.repo.LiveLinkGenerator
import com.ericdream.erictv.data.repo.LiveLinkGeneratorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * A hilt dagger module (setting for how to inject class)
 *
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class TvModule {
    @Binds
    abstract fun bindGenerator(impl: LiveLinkGeneratorImpl): LiveLinkGenerator

    @Binds
    abstract fun bindChannelRepo(impl: LiveChannelRepoImpl): LiveChannelRepo
}