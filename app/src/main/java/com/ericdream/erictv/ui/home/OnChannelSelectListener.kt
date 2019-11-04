package com.ericdream.erictv.ui.home

import com.ericdream.erictv.data.model.LiveChannel

interface OnChannelSelectListener {

    fun onChannelSelect(liveChannel: LiveChannel)
}
