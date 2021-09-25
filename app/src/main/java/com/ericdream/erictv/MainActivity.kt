package com.ericdream.erictv

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import timber.log.Timber
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
                    ChannelList(items = channels, onItemClick = {
                        Timber.d("click item icon")
                        val bundle = Bundle()
                        val uri = vm.getChannelLink(it)
                        Log.i("erictest", "uri $uri")
                        Log.i("erictest", "key ${it.key}")
                        it.link = uri.toString()
                        bundle.putSerializable(C.Key.LIVECHANNEL, it)
                        bundle.putParcelable(C.Key.URI, uri)
                        val a = it.key
                        navController.navigate("live/${it.key}")
                    })
                }
                composable("live/{channelId}") { backStackEntry ->
                    val channelId = backStackEntry.arguments?.getString("channelId") ?: ""
                    vm.loadChannelLinkById(channelId)
                    val link = remember {
                        vm.liveLink.value
                    }
                    VideoScreen(link)
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
