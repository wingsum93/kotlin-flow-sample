package com.ericdream.erictv.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericdream.erictv.data.model.LiveChannel
import com.ericdream.erictv.data.repo.LiveChannelRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: LiveChannelRepoImpl) : ViewModel(),
    KoinComponent {

    val text: MutableLiveData<String> = MutableLiveData<String>()

    var channels = mutableStateListOf<LiveChannel>()
        private set

    private val _liveLink =
        mutableStateOf("https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4")
    val liveLink: State<String> = _liveLink
    private val _pageTitle = mutableStateOf("Eric TV")
    val pageTitle: State<String> = _pageTitle

    init {
        loadChannel()
    }

    private fun loadChannel() {
        text.postValue("Hello X!")
        channels.clear()
        channels.addAll(repo.getLiveChannels())
    }

    fun loadChannelLinkById(id: String) {
        viewModelScope.launch {
            // check link if exist?
            val targetChannel = channels.find { it.key == id }
            if (targetChannel != null) {
                // set title
                _pageTitle.value = targetChannel.name
                if (targetChannel.link == null) {
                    val searchResult = repo.getLink(id)
                    if (searchResult.error) {
                        _liveLink.value = ""
                    } else {
                        _liveLink.value = searchResult.link!!
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

    fun resetToHomePageTitle() {
        _pageTitle.value = "Eric TV"
    }
}