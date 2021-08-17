package com.ericdream.erictv.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.ericdream.erictv.data.model.LiveChannel
import com.ericdream.erictv.theme.JetchatTheme

@Composable
fun ChannelScreen(
    viewModel: MainViewModel,
    onItemClick: (LiveChannel) -> Unit
) {
    JetchatTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            TopAppBar(title = {
                Text(text = "Eric TV")
            })
            for (item in viewModel.channels) {
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