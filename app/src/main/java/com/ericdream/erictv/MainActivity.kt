package com.ericdream.erictv

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.ericdream.erictv.theme.JetchatTheme
import com.ericdream.erictv.ui.PlayVideoAct
import com.ericdream.erictv.ui.home.ChannelScreen
import com.ericdream.erictv.ui.home.MainViewModel
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
        ChannelScreen(viewModel = vm, onItemClick = {
            Timber.d("click item icon")
            val bundle = Bundle()
            bundle.putSerializable(C.Key.LIVECHANNEL, it)
            if (it.link != null) {
                val uri = it.link!!.toUri()
                bundle.putParcelable(C.Key.URI, uri)
            }
            goToNextClass(PlayVideoAct::class to bundle)
        })
    }

    private fun goToNextClass(pair: Pair<KClass<*>, Bundle?>) {
        val intent = Intent(this, pair.first.java)
        pair.second?.let { intent.putExtras(it) }
        startActivity(intent)
    }

}
