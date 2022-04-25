@file:JvmName("MainAppKt")

package com.ericdream.erictv.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi

@ExperimentalCoilApi
@Composable
fun MainApp(
    navigationController: NavController,
    viewModel: MainViewModel,
    content: @Composable (PaddingValues) -> Unit,
) {
    val title = remember {
        viewModel.pageTitle
    }
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Row {
                    Text(text = title.value)
                }
            }
        )
    }, content = content)
}