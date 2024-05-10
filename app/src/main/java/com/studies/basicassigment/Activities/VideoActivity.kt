package com.studies.basicassigment.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
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
import com.studies.basicassigment.databinding.ActivityVideoBinding
import java.util.concurrent.TimeUnit


class VideoActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityVideoBinding.inflate(layoutInflater)
    }
    var currentPosition=0
    private var mediaController: MediaController? = null
    private lateinit var handler: Handler
    private lateinit var handler2: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        val receivedvideomodel = intent.getSerializableExtra("videomodel") as videomodel


        // Set media controller
        mediaController = MediaController(this)
        mediaController?.setAnchorView(binding.videoview2)
        binding.videoview2.setMediaController(null)



        val videoUrl = receivedvideomodel.video
        val videoUri = Uri.parse(videoUrl)
        binding.videoview2.setVideoURI(videoUri)


        // Set seekbar max value
        binding.videoview2.setOnPreparedListener {
            binding.seekbar.max = binding.videoview2.duration
            binding.videoview2PB.visibility = View.GONE
        }

        // Update seekbar and time text as video plays
        handler = Handler()
        handler2 = Handler()
        binding.videoview2.setOnCompletionListener {
            binding.seekbar.progress = 0
            updateSeekBarWithTime()
        }

        //intent
        binding.videolargeicon.setOnClickListener {
            val intent= Intent(this,VideoViewActivity::class.java)
            intent.putExtra("videoUrl",receivedvideomodel.video)
            intent.putExtra("currentPosition",currentPosition)
            startActivity(intent)
        }



        //pause and resume
        binding.playpausebutton.visibility=View.VISIBLE
        settinghandlerForVisiblity()
        binding.playpausebutton.setOnClickListener {
            if(binding.videoview2.isPlaying){
                binding.videoview2.pause()

                binding.playpausebutton.setImageResource(R.drawable.pause)

            }else{
                binding.videoview2.start()
                binding.playpausebutton.setImageResource(R.drawable.play)

            }
        }


        binding.videoview2.setOnClickListener {
            binding.playpausebutton.visibility=View.VISIBLE
            binding.videoduration.visibility=View.VISIBLE
            binding.videolargeicon.visibility=View.VISIBLE
            settinghandlerForVisiblity()
        }

        // Seekbar change listener

        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.videoview2.seekTo(progress)
                    currentPosition=progress
                    updateSeekBarWithTime()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Start updating seekbar and time text
        updateSeekBarWithTime()
        // Start the video
        binding.videoview2.start()








        binding.Title2.text=receivedvideomodel.title
        binding.ChannelName2.text=receivedvideomodel.channelname
        Glide.with(this).load(receivedvideomodel.channelogo).into(binding.channelImage)
    }


    private fun updateSeekBarWithTime() {
        handler.postDelayed({
            val currentTime =  binding.videoview2.currentPosition
            val totalTime =  binding.videoview2.duration

            // Update seekbar progress
            binding.seekbar.progress = currentTime

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
            binding.seekbar.progress = currentTime
            binding.seekbar.max = totalTime
            binding.seekbar.secondaryProgress =  binding.videoview2.bufferPercentage

            // Update time text
            val timeText = "$current / $total"
            binding.videoduration.text=timeText

            // Call the function again after 1 second
            updateSeekBarWithTime()
        }, 1000)
    }


    private fun settinghandlerForVisiblity(){
        handler2.removeCallbacksAndMessages(null)
        handler2.postDelayed({

           binding.playpausebutton.visibility=View.GONE
           binding.videoduration.visibility=View.GONE
           binding.videolargeicon.visibility=View.GONE

        },3000)
    }
}