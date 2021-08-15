package com.ericdream.erictv

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ericdream.erictv.ui.home.ChannelScreen
import com.ericdream.erictv.ui.home.MainViewModel
import kotlin.reflect.KClass

class MainActivity : ComponentActivity() {


//    private lateinit var viewDataBinding: ActivityMainBinding

//    private lateinit var adapter: ChannelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }


    }

    @Preview
    @Composable
    fun App(
        vm: MainViewModel = viewModel()
    ) {
        vm.loadChannel()
        ChannelScreen(viewModel = vm, onItemClick = {})
    }

    private fun goToNextClass(pair: Pair<KClass<*>, Bundle?>) {
        val intent = Intent(this, pair.first.java)
        pair.second?.let { intent.putExtras(it) }
        startActivity(intent)
    }
}
