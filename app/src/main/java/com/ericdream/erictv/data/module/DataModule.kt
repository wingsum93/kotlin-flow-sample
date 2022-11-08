package com.ericdream.erictv.data.module

import com.ericdream.erictv.data.repo.LiveChannelRepositoryImpl
import com.ericdream.erictv.data.repo.LiveLinkGeneratorImpl
import com.ericdream.erictv.data.repo.interfaces.LiveChannelRepository
import com.ericdream.erictv.data.repo.interfaces.LiveLinkGenerator
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
    abstract fun bindChannelRepo(impl: LiveChannelRepositoryImpl): LiveChannelRepository
}