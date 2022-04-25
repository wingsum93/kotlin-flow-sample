package com.ericdream.erictv

import com.ericdream.erictv.data.repo.LiveChannelRepo
import com.ericdream.erictv.data.repo.LiveChannelRepoImpl
import com.ericdream.erictv.data.repo.LiveLinkGenerator
import com.ericdream.erictv.data.repo.LiveLinkGeneratorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * A hilt dagger module (setting for how to inject class)
 *
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindGenerator(impl: LiveLinkGeneratorImpl): LiveLinkGenerator

    @Binds
    abstract fun bindChannelRepo(impl: LiveChannelRepoImpl): LiveChannelRepo
}