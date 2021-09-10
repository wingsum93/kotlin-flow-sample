package com.ericdream.erictv.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.ericdream.erictv.data.model.LiveChannel

@Composable
fun ChannelScreen(
    viewModel: MainViewModel,
    onItemClick: (LiveChannel) -> Unit
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
        Column(modifier = Modifier.fillMaxWidth()) {
            for (item in items) {
                Row(modifier = Modifier
                    .clickable { onItemClick(item) }
                    .fillMaxWidth()) {
                    Image(
                        painter = rememberImagePainter(item.iconLink),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(5.dp)
                            .width(50.dp)
                            .height(50.dp)
                    )
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(text = item.name, modifier = Modifier)
                    }
                }
            }
        }
    }
}