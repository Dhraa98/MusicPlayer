package com.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.musicplayer.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()
    private var pause: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initControls()
    }

    private fun initControls() {
        binding.lifecycleOwner = this
        playBtn.setOnClickListener {
            if (pause) {
                mediaPlayer.seekTo(mediaPlayer.currentPosition)
                mediaPlayer.start()
                pause = false
                Toast.makeText(this, "media playing", Toast.LENGTH_SHORT).show()
            } else {

                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.maroon)
                mediaPlayer.start()
                Toast.makeText(this, "media playing", Toast.LENGTH_SHORT).show()

            }
            playBtn.setColorFilter(
                ContextCompat.getColor(this, R.color.gray)
               
            );
            pauseBtn.setColorFilter(
                ContextCompat.getColor(this, R.color.black)
               
            );
            stopBtn.setColorFilter(
                ContextCompat.getColor(this, R.color.black)
               
            );


            playBtn.isEnabled = false
            pauseBtn.isEnabled = true
            stopBtn.isEnabled = true

            mediaPlayer.setOnCompletionListener {
                playBtn.isEnabled = true
                pauseBtn.isEnabled = false
                stopBtn.isEnabled = false
                Toast.makeText(this, "end", Toast.LENGTH_SHORT).show()
            }
        }
        // Pause the media player
        pauseBtn.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                pause = true
                playBtn.setColorFilter(
                    ContextCompat.getColor(this, R.color.black)
                   
                );
                pauseBtn.setColorFilter(
                    ContextCompat.getColor(this, R.color.gray)
                   
                );
                stopBtn.setColorFilter(
                    ContextCompat.getColor(this, R.color.black)
                   
                );

                playBtn.isEnabled = true
                pauseBtn.isEnabled = false
                stopBtn.isEnabled = true
                Toast.makeText(this, "media pause", Toast.LENGTH_SHORT).show()
            }
        }
        // Stop the media player
        stopBtn.setOnClickListener {
            if (mediaPlayer.isPlaying || pause.equals(true)) {
                pause = false

                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
                runnable = Runnable {

                    handler.postDelayed(this.runnable, 5000)
                }
                handler.removeCallbacks(runnable)
                playBtn.setColorFilter(
                    ContextCompat.getColor(this, R.color.black)
                );
                pauseBtn.setColorFilter(
                    ContextCompat.getColor(this, R.color.gray)
                );
                stopBtn.setColorFilter(
                    ContextCompat.getColor(this, R.color.gray)
                );


                playBtn.isEnabled = true
                pauseBtn.isEnabled = false
                stopBtn.isEnabled = false

                Toast.makeText(this, "media stop", Toast.LENGTH_SHORT).show()
            }

        }
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