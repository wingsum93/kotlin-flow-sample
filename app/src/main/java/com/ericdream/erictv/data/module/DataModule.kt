package com.ericdream.erictv.data.module

import com.ericdream.erictv.data.reposittory.LiveChannelRepositoryImpl
import com.ericdream.erictv.data.reposittory.LiveLinkGeneratorImpl
import com.ericdream.erictv.data.reposittory.interfaces.LiveChannelRepository
import com.ericdream.erictv.data.reposittory.interfaces.LiveLinkGenerator
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