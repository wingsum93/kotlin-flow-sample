package com.ericdream.erictv.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ericdream.erictv.C
import com.ericdream.erictv.ui.PlayVideoAct
import kotlin.reflect.KClass

class MainViewModel() : ViewModel() {

    val text: MutableLiveData<String> = MutableLiveData<String>()

    val targetClass = MutableLiveData<Pair<KClass<*>, Bundle?>>()

    fun start() {
        text.postValue("Hellow X!")
    }

    @SuppressWarnings("unused")
    fun processClick(view: View) {
        val uri = C.SAMPLE_HLS_LINK.toUri()

        //
        val bundle = Bundle()
        bundle.putParcelable(C.Key.URI, uri)
        targetClass.postValue(PlayVideoAct::class to bundle)
    }

}