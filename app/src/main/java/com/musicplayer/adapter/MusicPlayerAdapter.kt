package com.musicplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.musicplayer.data.MusicModel
import com.musicplayer.databinding.ItemPlayerAdapterBinding
import javax.security.auth.callback.Callback

class MusicPlayerAdapter(
    var musicList: MutableList<MusicModel> = mutableListOf(),
    private val callback: (position: Int) -> Unit
) :
    RecyclerView.Adapter<MusicPlayerAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemPlayerAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MusicPlayerAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlayerAdapterBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    override fun onBindViewHolder(holder: MusicPlayerAdapter.ViewHolder, position: Int) {
        holder.binding.tvMusic.text = musicList[position].musicName
        holder.itemView.setOnClickListener {
            callback(holder.adapterPosition)
        }
    }
}