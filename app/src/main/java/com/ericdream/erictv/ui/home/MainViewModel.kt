package com.ericdream.erictv.ui.home

import android.os.Bundle
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericdream.erictv.C
import com.ericdream.erictv.data.model.LiveChannel
import com.ericdream.erictv.data.repo.LiveChannelRepo
import com.ericdream.erictv.ui.PlayVideoAct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import timber.log.Timber
import kotlin.reflect.KClass

class MainViewModel() : ViewModel(), OnChannelSelectListener, KoinComponent {

    val text: MutableLiveData<String> = MutableLiveData<String>()

    val targetClass = MutableLiveData<Pair<KClass<*>, Bundle?>>()

    val channels: MutableLiveData<List<LiveChannel>> = MutableLiveData()

    private val repo: LiveChannelRepo = LiveChannelRepo()

    fun start() {
        text.postValue("Hellow X!")

        channels.postValue(repo.getLiveChannels())

    }


    override fun onChannelSelect(liveChannel: LiveChannel) {
        if (liveChannel.link != null) {
            val uri = liveChannel.link!!.toUri()

            val bundle = Bundle()
            viewModelScope.launch(Dispatchers.IO) {
                bundle.putParcelable(C.Key.URI, uri)

                val result = repo.getLink("viutv")

                if (result.error) {
                    Timber.e(result.exception)
                } else {
                    val link = result.link!!
                    Timber.d(link)
                    val uri = link.toUri()
                    val bundle = Bundle()
                    bundle.putParcelable(C.Key.URI, uri)
                    targetClass.postValue(PlayVideoAct::class to bundle)
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
    }
}