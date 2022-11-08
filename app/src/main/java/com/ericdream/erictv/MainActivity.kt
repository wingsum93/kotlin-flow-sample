package com.ericdream.erictv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ericdream.erictv.theme.JetchatTheme
import com.ericdream.erictv.ui.home.ChannelList
import com.ericdream.erictv.ui.home.MainApp
import com.ericdream.erictv.ui.home.MainViewModel
import com.ericdream.erictv.ui.playvideo.VideoScreen
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.layout.padding

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetchatTheme {
                App()
            }
        }
    }

    @ExperimentalAnimationApi
    @Preview
    @Composable
    fun App(
        vm: MainViewModel = hiltViewModel()
    ) {
        val channels = vm.channelList.collectAsState(initial = emptyList()).value
        val navController = rememberNavController()
        MainApp(navigationController = navController, vm) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home") {
                    vm.resetToHomePageTitle()
                    ChannelList(items = channels, navController = navController)
                }
                composable("live/{channelId}") { backStackEntry ->
                    val channelId = remember {
                        backStackEntry.arguments?.getString("channelId") ?: ""
                    }
                    val channel = remember {
                        channels.find { it.key == channelId }
                    }
                    remember(channel) {
                        vm.loadChannelLinkById(channel?.key ?: "")
                    }
                    val mediaPlayback = VideoScreen(channel?.link ?: "")
                }
            }
        }
    }
}
