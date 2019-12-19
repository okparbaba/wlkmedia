package com.wlkmultimedia.ui.main.utils;

import android.content.Context
import android.graphics.Point
import android.net.Uri

import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidwave.exoplayer.model.MediaObject

import com.bumptech.glide.RequestManager
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.wlkmultimedia.R
import com.wlkmultimedia.ui.main.adapters.feedadapter.PlayerViewHolder
import java.util.ArrayList
import java.util.Objects

/**
 * Created on : May 24, 2019
 * Author     : AndroidWave
 */
class ExoPlayerRecyclerView : RecyclerView {
    /**
     * PlayerViewHolder UI component
     * Watch PlayerViewHolder class
     */
    private var mediaCoverImage: ImageView? = null
    private var volumeControl: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var viewHolderParent: View? = null
    private var mediaContainer: FrameLayout? = null
    private var videoSurfaceView: PlayerView? = null
    private var videoPlayer: SimpleExoPlayer? = null
    /**
     * variable declaration
     */
    // Media List
    private var mediaObjects = ArrayList<MediaObject>()
    private var videoSurfaceDefaultHeight = 0
    private var screenDefaultHeight = 0

    private var playPosition = -1
    private var isVideoViewAdded: Boolean = false
    private var requestManager: RequestManager? = null
    // controlling volume state
    private var volumeState: VolumeState? = null
    private val videoViewClickListener = OnClickListener { toggleVolume() }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        val display = (Objects.requireNonNull(
                getContext().getSystemService(Context.WINDOW_SERVICE)) as WindowManager).defaultDisplay
        val point = Point()
        display.getSize(point)

        videoSurfaceDefaultHeight = point.x
        screenDefaultHeight = point.y

        videoSurfaceView = PlayerView(this.context)
        videoSurfaceView!!.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM)

        val bandwidthMeter = DefaultBandwidthMeter()
        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)

        //Create the player using ExoPlayerFactory
        videoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
        // Disable Player Control
        videoSurfaceView!!.useController = false
        // Bind the player to the view.
        videoSurfaceView!!.player = videoPlayer
        // Turn on Volume
        setVolumeControl(VolumeState.ON)

        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mediaCoverImage != null) {
                        // show the old thumbnail
                        mediaCoverImage!!.visibility = View.VISIBLE
                    }

                    // There's a special case when the end of the list has been reached.
                    // Need to handle that with this bit of logic
                    //ဗွီဒီယိုကို ပိတ်ဖွင့် လုပ်တာ

                    if (!recyclerView.canScrollVertically(1)) {
                        playVideo(true)
                    } else {
                        playVideo(false)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {

            }

            override fun onChildViewDetachedFromWindow(view: View) {
                if (viewHolderParent != null && viewHolderParent == view) {
                    resetVideoView()
                }
            }
        })

        videoPlayer!!.addListener(object : Player.EventListener {
            override fun onTimelineChanged(timeline: Timeline, manifest: Any?, reason: Int) {

            }

            override fun onTracksChanged(trackGroups: TrackGroupArray,
                                         trackSelections: TrackSelectionArray) {

            }

            override fun onLoadingChanged(isLoading: Boolean) {

            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {

                    Player.STATE_BUFFERING -> {
                        Log.e(TAG, "onPlayerStateChanged: Buffering video.")
                        if (progressBar != null) {
                            progressBar!!.visibility = View.VISIBLE
                        }
                    }
                    Player.STATE_ENDED -> {
                        Log.d(TAG, "onPlayerStateChanged: Video ended.")
                        videoPlayer!!.seekTo(0)
                    }
                    Player.STATE_IDLE -> {
                    }
                    Player.STATE_READY -> {
                        Log.e(TAG, "onPlayerStateChanged: Ready to play.")
                        if (progressBar != null) {
                            progressBar!!.visibility = View.GONE
                        }
                        if (!isVideoViewAdded) {
                            addVideoView()
                        }
                    }
                    else -> {
                    }
                }
            }

            override fun onRepeatModeChanged(repeatMode: Int) {

            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {

            }

            override fun onPlayerError(error: ExoPlaybackException) {

            }

            override fun onPositionDiscontinuity(reason: Int) {

            }

            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {

            }

            override fun onSeekProcessed() {

            }
        })
    }

    fun playVideo(isEndOfList: Boolean) {

        val targetPosition: Int

        if (!isEndOfList) {
            val startPosition = (Objects.requireNonNull<LayoutManager>(
                    layoutManager) as LinearLayoutManager).findFirstVisibleItemPosition()
            var endPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

            // if there is more than 2 list-items on the screen, set the difference to be 1
            if (endPosition - startPosition > 1) {
                endPosition = startPosition + 1
            }

            // something is wrong. return.
            if (startPosition < 0 || endPosition < 0) {
                return
            }

            // if there is more than 1 list-item on the screen
            if (startPosition != endPosition) {
                val startPositionVideoHeight = getVisibleVideoSurfaceHeight(startPosition)
                val endPositionVideoHeight = getVisibleVideoSurfaceHeight(endPosition)

                targetPosition = if (startPositionVideoHeight > endPositionVideoHeight) startPosition else endPosition
            } else {
                targetPosition = startPosition
            }
        } else {
            targetPosition = mediaObjects.size - 1
        }

        Log.d(TAG, "playVideo: target position: $targetPosition")

        // video is already playing so return
        if (targetPosition == playPosition) {
            return
        }

        // set the position of the list-item that is to be played
        playPosition = targetPosition
        if (videoSurfaceView == null) {
            return
        }

        // remove any old surface views from previously playing videos
        videoSurfaceView!!.visibility = View.INVISIBLE
        removeVideoView(videoSurfaceView!!)

        val currentPosition = targetPosition - (Objects.requireNonNull<LayoutManager>(
                layoutManager) as LinearLayoutManager).findFirstVisibleItemPosition()

        val child = getChildAt(currentPosition) ?: return

        val holder = child.tag as PlayerViewHolder
        if (holder == null) {
            playPosition = -1
            return
        }
        mediaCoverImage = holder.mediaCoverImage
        progressBar = holder.progressBar
        volumeControl = holder.volumeControl
        viewHolderParent = holder.itemView
        requestManager = holder.requestManager
        mediaContainer = holder.mediaContainer

        videoSurfaceView!!.player = videoPlayer
        viewHolderParent!!.setOnClickListener(videoViewClickListener)

        val dataSourceFactory = DefaultDataSourceFactory(
                context!!, Util.getUserAgent(context, AppName))
        val mediaUrl = mediaObjects[targetPosition].url
        if (mediaUrl != null) {
            val videoSource = ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(mediaUrl))
            videoPlayer!!.prepare(videoSource)
            videoPlayer!!.playWhenReady = true
        }
    }

    /**
     * Returns the visible region of the video surface on the screen.
     * if some is cut off, it will return less than the @videoSurfaceDefaultHeight
     */
    private fun getVisibleVideoSurfaceHeight(playPosition: Int): Int {
        val at = playPosition - (Objects.requireNonNull<LayoutManager>(
                layoutManager) as LinearLayoutManager).findFirstVisibleItemPosition()
        Log.d(TAG, "getVisibleVideoSurfaceHeight: at: $at")

        val child = getChildAt(at) ?: return 0

        val location = IntArray(2)
        child.getLocationInWindow(location)

        return if (location[1] < 0) {
            location[1] + videoSurfaceDefaultHeight
        } else {
            screenDefaultHeight - location[1]
        }
    }

    // Remove the old player
    private fun removeVideoView(videoView: PlayerView) {
        val parent = videoView.parent as? ViewGroup ?: return

        val index = parent.indexOfChild(videoView)
        if (index >= 0) {
            parent.removeViewAt(index)
            isVideoViewAdded = false
            viewHolderParent!!.setOnClickListener(null)
        }
    }

    private fun addVideoView() {
        mediaContainer!!.addView(videoSurfaceView)
        isVideoViewAdded = true
        videoSurfaceView!!.requestFocus()
        videoSurfaceView!!.visibility = View.VISIBLE
        videoSurfaceView!!.alpha = 1f
        mediaCoverImage!!.visibility = View.GONE
    }

    private fun resetVideoView() {
        if (isVideoViewAdded) {
            removeVideoView(videoSurfaceView!!)
            playPosition = -1
            videoSurfaceView!!.visibility = View.INVISIBLE
            mediaCoverImage!!.visibility = View.VISIBLE
        }
    }

    fun releasePlayer() {

        if (videoPlayer != null) {
            videoPlayer!!.release()
            videoPlayer = null
        }

        viewHolderParent = null
    }

    fun onPausePlayer() {
        if (videoPlayer != null) {
            videoPlayer!!.stop(true)
        }
    }

    private fun toggleVolume() {
        if (videoPlayer != null) {
            if (volumeState == VolumeState.OFF) {
                Log.d(TAG, "togglePlaybackState: enabling volume.")
                setVolumeControl(VolumeState.ON)
            } else if (volumeState == VolumeState.ON) {
                Log.d(TAG, "togglePlaybackState: disabling volume.")
                setVolumeControl(VolumeState.OFF)
            }
        }
    }

    //public void onRestartPlayer() {
    //  if (videoPlayer != null) {
    //   playVideo(true);
    //  }
    //}

    private fun setVolumeControl(state: VolumeState) {
        volumeState = state
        if (state == VolumeState.OFF) {
            videoPlayer!!.volume = 0f
            animateVolumeControl()
        } else if (state == VolumeState.ON) {
            videoPlayer!!.volume = 1f
            animateVolumeControl()
        }
    }

    private fun animateVolumeControl() {
        if (volumeControl != null) {
            volumeControl!!.bringToFront()
            if (volumeState == VolumeState.OFF) {
                requestManager!!.load(R.drawable.ic_volume_off)
                        .into(volumeControl!!)
            } else if (volumeState == VolumeState.ON) {
                requestManager!!.load(R.drawable.ic_volume_on)
                        .into(volumeControl!!)
            }
            volumeControl!!.animate().cancel()

            volumeControl!!.alpha = 1f

            volumeControl!!.animate()
                    .alpha(0f)
                    .setDuration(600).startDelay = 1000
        }
    }

    fun setMediaObjects(mediaObjects: ArrayList<MediaObject>) {
        this.mediaObjects = mediaObjects
    }

    /**
     * Volume ENUM
     */
    private enum class VolumeState {
        ON, OFF
    }

    companion object {

        private val TAG = "ExoPlayerRecyclerView"
        private val AppName = "Android ExoPlayer"
    }
}

