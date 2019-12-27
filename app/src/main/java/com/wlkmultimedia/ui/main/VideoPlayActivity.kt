package com.wlkmultimedia.ui.main

import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util
import com.wlkmultimedia.R
import kotlinx.android.synthetic.main.activity_video_play.*
import kotlinx.android.synthetic.main.exo_player_control_view.*

class VideoPlayActivity : AppCompatActivity() {
    private lateinit var player: SimpleExoPlayer
    val videolink =
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)
        ivwidesmall?.setOnClickListener {
            if (!isWide){
                isWide = true
                Log.e("wide",isWide.toString())
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

            }else{

                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                isWide = false

            }
        }
    }
    companion object{
        var isWide = false
    }

    override fun onStart() {
        super.onStart()
        player = ExoPlayerFactory.newSimpleInstance(
            this,
            DefaultTrackSelector()
        )
        player_view.player = player
        val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "exo-demo"))
        val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(videolink))
        player.prepare(mediaSource)
        player.playWhenReady = true
        player.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)
                if (playbackState == ExoPlayer.STATE_BUFFERING) {
                    loadingVideo.visibility = View.VISIBLE
                } else {
                    loadingVideo.visibility = View.GONE
                }
            }

        })
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        //we use onSaveInstanceState in order to store the video playback position for orientation change
        savedInstanceState.putLong("Position", player.currentPosition)

    }

    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //we use onRestoreInstanceState in order to play the video playback from the stored position
        val position = savedInstanceState.getLong("Position")
        player.seekTo(position)
    }

    override fun onStop() {
        super.onStop()
        player_view.player = null
        player.release()
    }

    override fun onBackPressed() {
        if (isWide){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            isWide = false
        }else super.onBackPressed()

    }

}
