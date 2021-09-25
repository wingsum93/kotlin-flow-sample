package com.ericdream.erictv.ui.home

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import coil.annotation.ExperimentalCoilApi
import com.ericdream.erictv.data.model.LiveChannel

@ExperimentalCoilApi
@Composable
fun ChannelScreen(
    viewModel: MainViewModel,
    onItemClick: suspend (LiveChannel) -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Row {
                    Text(text = "Eric TV")
                }
            }
        )
    }) {
        val items = remember {
            viewModel.channels
        }
        ChannelList(items = items, onItemClick = onItemClick)
    }
}