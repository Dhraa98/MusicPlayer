package com.musicplayer.data

class MusicModel {
    var musicFile: Int = 0

    var musicName: String = ""


    fun getmusicFile(): Int? {
        return musicFile
    }

    fun setmusicFile(name: Int) {
        musicFile = musicFile
    }


    fun getmusicName(): String? {
        return musicName
    }

    fun setmusicName(name: String) {
        musicName = musicName
    }
}