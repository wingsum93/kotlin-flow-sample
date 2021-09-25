package com.ericdream.erictv.ui.home

import android.net.Uri
import android.os.Bundle
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericdream.erictv.data.model.LiveChannel
import com.ericdream.erictv.data.repo.LiveChannelRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
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

    private val _liveLink = mutableStateOf("")
    val liveLink: State<String> = _liveLink
    private val _pageTitle = mutableStateOf("Eric TV")
    val pageTitle: State<String> = _pageTitle

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

    fun loadChannelLinkById(id: String) {
        viewModelScope.launch {
            // check link if exist?
            val targetChannel = channels.find { it.key == id }
            if (targetChannel != null) {
                // set title
                _pageTitle.value = targetChannel.name
                if (targetChannel.link == null) {
                    val a = repo.getLink(id)
                    if (a.error) {
                        _liveLink.value = ""
                    } else {
                        _liveLink.value = a.link!!
                    }
                } else {
                    _liveLink.value = targetChannel.link!!
                }
            }
        }
    }


    override fun onCleared() {
        viewModelScope.coroutineContext.cancel()
        super.onCleared()
    }
}