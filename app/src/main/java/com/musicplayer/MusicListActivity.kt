package com.musicplayer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.musicplayer.adapter.MusicPlayerAdapter
import com.musicplayer.data.MusicModel
import com.musicplayer.databinding.ActivityMusicListBinding
import com.musicplayer.utils.musicList
import kotlinx.android.synthetic.main.activity_music_list.*

class MusicListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMusicListBinding
    private lateinit var adapter: MusicPlayerAdapter
    private lateinit var manager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_music_list)
        initControls()

    }

    private fun initControls() {
        binding.lifecycleOwner = this
        if (musicList.size > 0) {
            musicList.clear()
        }
        var model: MusicModel = MusicModel()
        model.musicFile = R.raw.maroon
        model.musicName = "Girls like you"
        musicList.add(model)

        var model1: MusicModel = MusicModel()
        model1.musicFile = R.raw.senorita
        model1.musicName = "Senorita"
        musicList.add(model1)

        var model2: MusicModel = MusicModel()
        model2.musicFile = R.raw.despacito
        model2.musicName = "Despacito"
        musicList.add(model2)

        var model3: MusicModel = MusicModel()
        model3.musicFile = R.raw.memories
        model3.musicName = "Memories"
        musicList.add(model3)

        var model4: MusicModel = MusicModel()
        model4.musicFile = R.raw.lovemelikeyoudo
        model4.musicName = "Love me like you do"
        musicList.add(model4)


        adapter = MusicPlayerAdapter(musicList, { position ->
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("ListPos", position)
            startActivity(intent)

        })
        manager = LinearLayoutManager(this)
        rvList.adapter = adapter
        rvList.layoutManager = manager

    }
}