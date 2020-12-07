package com.musicplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.musicplayer.databinding.ActivityMainBinding
import com.musicplayer.utils.musicList
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.String
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()
    private var pause: Boolean = true
    private var listPosition: Int = 0
    private var flag: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initControls()
    }

    private fun initControls() {
        binding.lifecycleOwner = this
        listPosition = intent.getIntExtra("ListPos", 0)
        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer!!.seekTo(progress * 1000);
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        playBtn.setOnClickListener {

            if (!pause) {
                if (mediaPlayer!!.isPlaying) {
                    mediaPlayer!!.pause()
                    pause = true
                    playBtn.setImageResource(R.drawable.play)
                    Toast.makeText(this, "media playing", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (mediaPlayer == null) {
                    mediaPlayer =
                        MediaPlayer.create(applicationContext, musicList[listPosition].musicFile)
                } else {
                    mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition)
                }
                mediaPlayer!!.start()
                tvSongName.text = musicList[listPosition].musicName
                playBtn.setImageResource(R.drawable.pause)
                pause = false
                Toast.makeText(this, "media paused", Toast.LENGTH_SHORT).show()
            }


            initializeSeekBar()


            mediaPlayer!!.setOnCompletionListener {
                playBtn.isEnabled = true
                pauseBtn.isEnabled = false
                stopBtn.isEnabled = false
                Toast.makeText(this, "end", Toast.LENGTH_SHORT).show()
            }
        }

        // Stop the media player
        stopBtn.setOnClickListener {
            if (mediaPlayer!!.isPlaying || pause.equals(true)) {
                pause = false
                seek_bar.setProgress(0)
                mediaPlayer!!.stop()
                mediaPlayer!!.reset()
                mediaPlayer!!.release()

                handler.removeCallbacks(runnable)

                tv_pass.text = ""
                tv_due.text = ""
                playBtn.isEnabled = true
                pauseBtn.isEnabled = false
                stopBtn.isEnabled = false

                Toast.makeText(this, "media stop", Toast.LENGTH_SHORT).show()
            }

        }
        prev.setOnClickListener {
            if (mediaPlayer!!.isPlaying || pause.equals(true)) {
                pause = false
                seek_bar.setProgress(0)
                mediaPlayer!!.stop()
                mediaPlayer!!.reset()
                mediaPlayer!!.release()
                tv_pass.text = ""
                tv_due.text = ""
                handler.removeCallbacks(runnable)

                if (listPosition == 0) {
                    listPosition = musicList.size - 1

                } else {
                    listPosition = listPosition - 1
                }
                flag == 1

                mediaPlayer =
                    MediaPlayer.create(applicationContext, musicList[listPosition].musicFile)
                mediaPlayer!!.start()
                tvSongName.text = musicList[listPosition].musicName
                initializeSeekBar()
                Toast.makeText(this, "media playing", Toast.LENGTH_SHORT).show()
            }
        }
        next.setOnClickListener {
            if (mediaPlayer!!.isPlaying || pause.equals(true)) {
                pause = false
                seek_bar.setProgress(0)
                mediaPlayer!!.stop()
                mediaPlayer!!.reset()
                mediaPlayer!!.release()
                tv_pass.text = ""
                tv_due.text = ""
                handler.removeCallbacks(runnable)

                if (listPosition == musicList.size - 1) {
                    listPosition = 0

                } else {
                    listPosition = listPosition + 1
                }
                flag == 1
                mediaPlayer =
                    MediaPlayer.create(applicationContext, musicList[listPosition].musicFile)
                mediaPlayer!!.start()
                tvSongName.text = musicList[listPosition].musicName
                initializeSeekBar()
                Toast.makeText(this, "media playing", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initializeSeekBar() {
        seek_bar.max = mediaPlayer!!.seconds

        runnable = Runnable {
            seek_bar.progress = mediaPlayer!!.currentSeconds
            val duration = mediaPlayer!!.duration
            val time = String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration.toLong()))
            )
            // tv_pass.text = "${mediaPlayer!!.currentSeconds} sec"

            val diff = mediaPlayer!!.seconds - mediaPlayer!!.currentSeconds
            // tv_due.text = "$diff sec"
            val seconds = diff % 60;
            tv_pass.text = "0${((diff % 3600) / 60).toString()}:$seconds"
            tv_due.text = time

            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)

    }

    override fun onPause() {

        if (mediaPlayer != null) {
            stopBtn.callOnClick()
        }
        super.onPause()
    }

    override fun onDestroy() {

        if (mediaPlayer != null) {
            stopBtn.callOnClick()
        }
        super.onDestroy()
    }


}

// Creating an extension property to get the media player time duration in seconds
val MediaPlayer.seconds: Int
    get() {
        return this.duration / 1000
    }

// Creating an extension property to get media player current position in seconds
val MediaPlayer.currentSeconds: Int
    get() {
        return this.currentPosition / 1000
    }