package com.wlkmultimedia.ui.main.feedadapter

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidwave.exoplayer.model.MediaObject

import com.bumptech.glide.RequestManager
import com.wlkmultimedia.R

/**
 * Created on : May 24, 2019
 * Author     : AndroidWave
 */
class PlayerViewHolder(private val parent: View) : RecyclerView.ViewHolder(parent) {

    /**
     * below view have public modifier because
     * we have access PlayerViewHolder inside the ExoPlayerRecyclerView
     */
    var mediaContainer: FrameLayout
    var mediaCoverImage: ImageView
    var volumeControl: ImageView
    var progressBar: com.github.ybq.android.spinkit.SpinKitView
    lateinit var requestManager: RequestManager
    private val title: TextView
    private val userHandle: TextView

    init {
        mediaContainer = parent.findViewById(R.id.mediaContainer)
        mediaCoverImage = parent.findViewById(R.id.ivMediaCoverImage)
        title = parent.findViewById(R.id.tvTitle)
        userHandle = parent.findViewById(R.id.tvUserHandle)
        progressBar = parent.findViewById(R.id.progressBar)
        volumeControl = parent.findViewById(R.id.ivVolumeControl)
    }

    internal fun onBind(mediaObject: MediaObject, requestManager: RequestManager) {
        this.requestManager = requestManager
        parent.tag = this
        title.text = mediaObject.title
        userHandle.text = mediaObject.userHandle
        this.requestManager
                .load(mediaObject.coverUrl)
                .into(mediaCoverImage)
    }
}

