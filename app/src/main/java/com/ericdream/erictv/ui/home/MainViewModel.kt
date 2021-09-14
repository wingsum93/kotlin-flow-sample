package com.ericdream.erictv.ui.home

import android.net.Uri
import android.os.Bundle
import androidx.compose.runtime.mutableStateListOf
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericdream.erictv.C
import com.ericdream.erictv.data.model.LiveChannel
import com.ericdream.erictv.data.repo.LiveChannelRepoImpl
import com.ericdream.erictv.ui.PlayVideoAct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.KClass

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: LiveChannelRepoImpl) : ViewModel(),
    OnChannelSelectListener, KoinComponent {

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


    override fun onChannelSelect(liveChannel: LiveChannel) {


        var uri: Uri
        val bundle = Bundle()
        bundle.putSerializable(C.Key.LIVECHANNEL, liveChannel)
        viewModelScope.launch(Dispatchers.IO) {
            if (liveChannel.link != null) {
                uri = liveChannel.link!!.toUri()
                bundle.putParcelable(C.Key.URI, uri)
            } else {
                val result = repo.getLink(liveChannel.key)
                if (result.error) {
                    Timber.e(result.exception)
                } else {
                    val link = result.link!!
                    Timber.d(link)
                    uri = link.toUri()

                    bundle.putParcelable(C.Key.URI, uri)

                }
            }
            targetClass.postValue(PlayVideoAct::class to bundle)
        }
    }


    override fun onCleared() {
        viewModelScope.coroutineContext.cancel()
        super.onCleared()
    }
}