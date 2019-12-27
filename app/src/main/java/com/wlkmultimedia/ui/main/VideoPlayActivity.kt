package com.wlkmultimedia.ui.main

import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.wlkmultimedia.ui.main.adapters.PlayerViewRelatedRecyclerAdapter
import com.wlkmultimedia.ui.main.model.PlayerViewRelatedObject
import kotlinx.android.synthetic.main.activity_video_play.*
import kotlinx.android.synthetic.main.exo_player_control_view.*

class VideoPlayActivity : AppCompatActivity() {
    private lateinit var player: SimpleExoPlayer
    val videolink =
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    private lateinit var adapter : PlayerViewRelatedRecyclerAdapter
    private lateinit var manager: LinearLayoutManager
    private lateinit var items:ArrayList<PlayerViewRelatedObject>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)
        ivwidesmall?.setOnClickListener {
            if (!isWide){
                isWide = true
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }else{
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                isWide = false
            }
        }

        items = ArrayList()
        manager = LinearLayoutManager(this)
        manager.orientation = RecyclerView.HORIZONTAL
        adapter = PlayerViewRelatedRecyclerAdapter(getItems())
        rvRelated?.layoutManager = manager
        rvRelated?.adapter = adapter

    }
    companion object{
        var isWide = false
    }
    private fun getItems():ArrayList<PlayerViewRelatedObject>{
        val p0 = PlayerViewRelatedObject(imageLink = "https://images-na.ssl-images-amazon.com/images/I/8150KG7y2EL.jpg",title = "Hindenburg-The Aden Garden")
        items.add(p0)

        val p1 = PlayerViewRelatedObject(imageLink = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQMk9luFuIIMxYJPv9e-ebclXeQyjPCT9YPMkbjH_Bo5Fb7liO0fw&s",title = "Advanger-The Aden Garden")
        items.add(p1)

        val p3 = PlayerViewRelatedObject(imageLink = "https://www.digitalartsonline.co.uk/cmsdata/slideshow/3662115/baby-driver-rory-hi-res.jpg",title = "Hindenburg-The Aden Garden")
        items.add(p3)

        val p4 = PlayerViewRelatedObject(imageLink = "https://cdn.shopify.com/s/files/1/3002/1810/products/postersite_620x.jpg?v=1575456021",title = "Hindenburg-The Aden Garden")
        items.add(p4)

        val p5 = PlayerViewRelatedObject(imageLink = "https://cdn.shopify.com/s/files/1/3002/1810/products/postersite_620x.jpg?v=1575456021",title = "Hindenburg-The Aden Garden")
        items.add(p5)

        val p6 = PlayerViewRelatedObject(imageLink = "https://cdn.shopify.com/s/files/1/3002/1810/products/postersite_620x.jpg?v=1575456021",title = "Hindenburg-The Aden Garden")
        items.add(p6)

        val p7 = PlayerViewRelatedObject(imageLink = "https://cdn.shopify.com/s/files/1/3002/1810/products/postersite_620x.jpg?v=1575456021",title = "Hindenburg-The Aden Garden")
        items.add(p7)

        val p8 = PlayerViewRelatedObject(imageLink = "https://cdn.shopify.com/s/files/1/3002/1810/products/postersite_620x.jpg?v=1575456021",title = "Hindenburg-The Aden Garden")
        items.add(p8)
        return items
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
