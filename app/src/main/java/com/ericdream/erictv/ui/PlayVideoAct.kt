package com.ericdream.erictv.ui

import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ericdream.erictv.C
import com.ericdream.erictv.R
import com.ericdream.erictv.data.model.LiveChannel
import com.ericdream.erictv.databinding.ActPlayVideoBinding
import com.ericdream.erictv.ui.playvideo.VideoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class PlayVideoAct : AppCompatActivity() {


    lateinit var binding: ActPlayVideoBinding
    private val viewModel by viewModel<VideoViewModel>()


    private lateinit var uri: Uri
    private lateinit var liveChannel: LiveChannel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.act_play_video)

        binding.lifecycleOwner = this
        binding.vm = viewModel
        //get data

        uri = intent.getParcelableExtra(C.Key.URI) as Uri
        liveChannel = intent.getSerializableExtra(C.Key.LIVECHANNEL) as LiveChannel



        viewModel.player.observe(this, Observer {
            binding.playerView?.player = it
            Timber.i("Player updated !!")
            it?.playWhenReady = true
        })
        viewModel.videoPlay.observe(this, Observer { playState ->

        })

        // Start video
        viewModel.init(uri, liveChannel)


        title = liveChannel.name

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

    }


    override fun onStart() {
        super.onStart()
        viewModel.setUpPlayer()
    }

    override fun onStop() {
        viewModel.releasePlayer()
        super.onStop()
    }
    override fun onDestroy() {

        super.onDestroy()
    }
}