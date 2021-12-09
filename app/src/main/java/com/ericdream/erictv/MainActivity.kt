package com.ericdream.erictv

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.ericdream.erictv.ui.home.VideoScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

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

    @Preview
    @Composable
    fun App(
        vm: MainViewModel = hiltViewModel()
    ) {
        val channels = remember {
            vm.channels
        }
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
                    remember {
                        vm.loadChannelLinkById(channelId)
                        true
                    }
                    val link = remember {
                        vm.liveLink
                    }
                    val mediaPlayback = VideoScreen(link.value)
                }
            }
        }
    }

    private fun goToNextClass(pair: Pair<KClass<*>, Bundle?>) {
        val intent = Intent(this, pair.first.java)
        pair.second?.let { intent.putExtras(it) }
        startActivity(intent)
    }

}
