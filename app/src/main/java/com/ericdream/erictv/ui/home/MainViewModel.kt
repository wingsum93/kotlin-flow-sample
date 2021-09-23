package com.ericdream.erictv.ui.home

import android.net.Uri
import android.os.Bundle
import androidx.compose.runtime.mutableStateListOf
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericdream.erictv.data.model.LiveChannel
import com.ericdream.erictv.data.repo.LiveChannelRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import org.koin.core.KoinComponent
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.KClass

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: LiveChannelRepoImpl) : ViewModel(),
    KoinComponent {

    val text: MutableLiveData<String> = MutableLiveData<String>()

    val targetClass = MutableLiveData<Pair<KClass<*>, Bundle?>>()

    var channels = mutableStateListOf<LiveChannel>()
        private set

    init {
        loadChannel()
    }

    private fun loadChannel() {
        text.postValue("Hellow X!")
        channels.clear()
        channels.addAll(repo.getLiveChannels())
    }


    suspend fun getChannelLink(liveChannel: LiveChannel): Uri? {
        var uri: Uri? = null
        if (liveChannel.link != null) {
            uri = liveChannel.link!!.toUri()
        } else {
            val result = repo.getLink(liveChannel.key)
            if (result.error) {
                Timber.e(result.exception, "unable to find link")
            } else {
                val link = result.link!!
                Timber.i("link-> $link")
                uri = link.toUri()
            }
        }
        return uri
    }


    override fun onCleared() {
        viewModelScope.coroutineContext.cancel()
        super.onCleared()
    }
}