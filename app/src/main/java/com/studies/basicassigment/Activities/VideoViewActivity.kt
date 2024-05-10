package com.studies.basicassigment.Activities

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.MediaController
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.studies.basicassigment.Models.videomodel
import com.studies.basicassigment.R
import com.studies.basicassigment.databinding.ActivityVideoViewBinding
import java.util.concurrent.TimeUnit

class VideoViewActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityVideoViewBinding.inflate(layoutInflater)
    }
    private var mediaController: MediaController? = null
    private lateinit var handler: Handler
    private lateinit var handler2: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     enableEdgeToEdge()
       setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        supportActionBar?.hide()
        actionBar?.hide()
        // Set media controller
        mediaController = MediaController(this)
        mediaController?.setAnchorView(binding.videoview3)
        binding.videoview3.setMediaController(null)


        val videoUrl = intent.getSerializableExtra("videoUrl") as String
        val currentPosition = intent.getSerializableExtra("currentPosition") as Int


        val videoUri = Uri.parse(videoUrl)
        binding.videoview3.setVideoURI(videoUri)
        Log.d("@@@@@",currentPosition.toString())
      binding.seekbar2.progress = currentPosition
        binding.videoview3.seekTo(currentPosition)


        // Set seekbar max value
        binding.videoview3.setOnPreparedListener {
            binding.seekbar2.max = binding.videoview3.duration
            binding.videoview2PB2.visibility = View.GONE
        }

        // Update seekbar and time text as video plays
        handler = Handler()
        handler2 = Handler()
        binding.videoview3.setOnCompletionListener {
            binding.seekbar2.progress = 0
            updateSeekBarWithTime()
        }


        //pause and resume
        binding.playpausebutton2.visibility = View.VISIBLE
        settinghandlerForVisiblity()
        binding.playpausebutton2.setOnClickListener {
            if (binding.videoview3.isPlaying) {
                binding.videoview3.pause()

                binding.playpausebutton2.setImageResource(R.drawable.pause)

            } else {
                binding.videoview3.start()
                binding.playpausebutton2.setImageResource(R.drawable.play)

            }
        }


        binding.videoview3.setOnClickListener {
            binding.playpausebutton2.visibility = View.VISIBLE
            binding.playpausebutton2.visibility = View.VISIBLE
            binding.playpausebutton2.visibility = View.VISIBLE
            settinghandlerForVisiblity()
        }

        // Seekbar change listener

        binding.seekbar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.videoview3.seekTo(progress)
                    updateSeekBarWithTime()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Start updating seekbar and time text
        updateSeekBarWithTime()
        // Start the video
        binding.videoview3.start()


    }


    private fun updateSeekBarWithTime() {
        handler.postDelayed({
            val currentTime = binding.videoview3.currentPosition
            val totalTime = binding.videoview3.duration

            // Update seekbar progress
            binding.seekbar2.progress = currentTime

            // Update time text
            val current = String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(currentTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(currentTime.toLong()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentTime.toLong()))
            )
            val total = String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(totalTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(totalTime.toLong()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTime.toLong()))
            )

            // Set text to seekbar
            binding.seekbar2.progress = currentTime
            binding.seekbar2.max = totalTime
            binding.seekbar2.secondaryProgress = binding.videoview3.bufferPercentage

            // Update time text
            val timeText = "$current / $total"
            binding.videoduration2.text = timeText

            // Call the function again after 1 second
            updateSeekBarWithTime()
        }, 1000)
    }


    private fun settinghandlerForVisiblity() {
        handler2.removeCallbacksAndMessages(null)
        handler2.postDelayed({

            binding.playpausebutton2.visibility = View.GONE
            binding.videoduration2.visibility = View.GONE


        }, 3000)
    }
}