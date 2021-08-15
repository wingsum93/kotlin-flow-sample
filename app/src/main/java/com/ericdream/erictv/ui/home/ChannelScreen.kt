package com.ericdream.erictv.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.ericdream.erictv.data.model.LiveChannel
import com.ericdream.erictv.theme.JetchatTheme

@Preview
@Composable
fun ChannelScreen(
    viewModel: MainViewModel,
    onItemClick: (LiveChannel) -> Unit
) {
    JetchatTheme {
        Column {
            val a = viewModel.channels.firstStateRecord
            for (item in viewModel.channels) {
                Row() {
                    Image(
                        painter = rememberImagePainter(item.iconLink),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { onItemClick(item) }
                            .width(80.dp)
                            .height(50.dp)
                    )
                    Text(text = item.name)
                }

            }
        }
    }
}