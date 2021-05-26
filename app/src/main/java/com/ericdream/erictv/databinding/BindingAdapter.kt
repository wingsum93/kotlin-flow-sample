package com.ericdream.erictv.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ericdream.erictv.R
import com.ericdream.erictv.data.model.LiveChannel
import com.ericdream.erictv.ui.home.ChannelAdapter


@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(string: String?) {
    string?.let {
        Glide
            .with(this)
            .load(string)
            .error(R.drawable.ic_baseline_help_24)
            .into(this)

    }
}

@BindingAdapter("items")
fun RecyclerView.setLiveChannels(list: List<LiveChannel>?) {
    val adapter = this.adapter
    if (adapter is ChannelAdapter) {
        list?.let {
            adapter.update(it)
        }
    }

}

@BindingAdapter("android:src")
fun ImageView.setImageViewResource(int: Int) {
    this.setImageResource(int)

}