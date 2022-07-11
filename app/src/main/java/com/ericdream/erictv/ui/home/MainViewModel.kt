package com.ericdream.erictv.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericdream.erictv.data.model.LiveChannel
import com.ericdream.erictv.data.repo.LiveChannelRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: LiveChannelRepoImpl) : ViewModel(),
    KoinComponent {

    val text: MutableLiveData<String> = MutableLiveData<String>()

    private var channels = emptyList<LiveChannel>()

    private val _liveLink =
        mutableStateOf("https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4")

    private val _pageTitle = mutableStateOf("Eric TV")
    val pageTitle: State<String> = _pageTitle

    val channelList = repo.getLiveChannels()

    init {
        viewModelScope.launch {
            channelList.collectLatest { channels = it }
        }
    }

    fun loadChannelLinkById(id: String) {
        viewModelScope.launch {
            // check link if exist?
            val targetChannel = channels.find { it.key == id }
            if (targetChannel != null) {
                // set title
                _pageTitle.value = targetChannel.name
            }
        }
    }

    override fun onCleared() {
        viewModelScope.coroutineContext.cancel()
        super.onCleared()
    }

    fun resetToHomePageTitle() {
        _pageTitle.value = "Eric TV Flow"
    }
}