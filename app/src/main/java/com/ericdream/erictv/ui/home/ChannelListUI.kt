package com.ericdream.erictv.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.ericdream.erictv.data.model.LiveChannel
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.*

@Composable
fun ChannelListUI(
    items: List<LiveChannel>,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxWidth()) {
        for (item in items) {
            Button(
                onClick = {
                    scope.launch {
                        navController.navigate("live/${item.key}")
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 10.dp)
                    .widthIn(min = 50.dp, max = 250.dp),
                shape = RoundedCornerShape(50.dp),
                border = BorderStroke(
                    2.dp,
                    Color.Gray
                ),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                ) {
                    Image(
                        painter = rememberImagePainter(item.iconLink),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(30.dp)
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