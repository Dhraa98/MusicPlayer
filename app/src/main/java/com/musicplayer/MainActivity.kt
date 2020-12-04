package com.musicplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.musicplayer.databinding.ActivityMainBinding

import kotlinx.android.synthetic.main.activity_main.*
import java.lang.String
import java.util.concurrent.TimeUnit


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

            initializeSeekBar()
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
                seek_bar.setProgress(0)
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()

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

                tv_pass.text = ""
                tv_due.text = ""
                playBtn.isEnabled = true
                pauseBtn.isEnabled = false
                stopBtn.isEnabled = false

                Toast.makeText(this, "media stop", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun initializeSeekBar() {
        seek_bar.max = mediaPlayer.seconds

        runnable = Runnable {
            seek_bar.progress = mediaPlayer.currentSeconds
            val duration = mediaPlayer.duration
            val time = String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration.toLong()))
            )
            // tv_pass.text = "${mediaPlayer.currentSeconds} sec"

            val diff = mediaPlayer.seconds - mediaPlayer.currentSeconds
            // tv_due.text = "$diff sec"
            val seconds = diff % 60;
            tv_pass.text = "0${((diff % 3600) / 60).toString()}:$seconds"
            tv_due.text = time

            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)

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