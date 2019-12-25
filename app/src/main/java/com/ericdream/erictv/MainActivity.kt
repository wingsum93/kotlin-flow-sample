package com.ericdream.erictv

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ericdream.erictv.databinding.ActivityMainBinding
import com.ericdream.erictv.ui.home.ChannelAdapter
import com.ericdream.erictv.ui.home.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    private lateinit var viewDataBinding: ActivityMainBinding

    private lateinit var adapter: ChannelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        viewDataBinding.lifecycleOwner = this
        viewDataBinding.vm = viewModel

        adapter = ChannelAdapter(this, viewModel)
        viewDataBinding.recyclerView.adapter = adapter
        viewDataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        viewDataBinding.recyclerView.setHasFixedSize(true)

        viewModel.targetClass.observe(this, Observer {
            goToNextClass(it)
        })

        viewModel.loadChannel()



    }

    private fun goToNextClass(pair: Pair<KClass<*>, Bundle?>) {
        val intent = Intent(this, pair.first.java)
        pair.second?.let { intent.putExtras(it) }
        startActivity(intent)
    }
}
