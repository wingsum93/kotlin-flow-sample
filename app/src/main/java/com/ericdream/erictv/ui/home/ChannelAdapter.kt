package com.ericdream.erictv.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericdream.erictv.R
import com.ericdream.erictv.data.model.LiveChannel
import com.ericdream.erictv.databinding.RowChannelBinding

class ChannelAdapter(
    private val context: Context,
    private val onChannelSelectListener: OnChannelSelectListener?
) :
    RecyclerView.Adapter<ChannelAdapter.ViewHolder>() {

    private var items: MutableList<LiveChannel> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<RowChannelBinding>(
            layoutInflater,
            R.layout.row_channel,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.bind(item, onChannelSelectListener)

    }

    fun update(update: List<LiveChannel>) {
        items.clear()
        items.addAll(update)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: RowChannelBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(liveChannel: LiveChannel, listener: OnChannelSelectListener?) {
            binding.item = liveChannel
            binding.linearLayout.setOnClickListener {
                listener?.onChannelSelect(liveChannel)
            }
        }
    }
}