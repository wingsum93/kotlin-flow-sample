package com.ericdream.erictv.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.ericdream.erictv.R


@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(string: String?) {
    load(string) {
        error(R.drawable.ic_baseline_help_24)
    }
}

@BindingAdapter("android:src")
fun ImageView.setImageViewResource(int: Int) {
    this.setImageResource(int)
}