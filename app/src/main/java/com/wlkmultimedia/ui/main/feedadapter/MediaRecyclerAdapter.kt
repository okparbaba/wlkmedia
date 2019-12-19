package com.wlkmultimedia.ui.main.feedadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidwave.exoplayer.model.MediaObject
import com.bumptech.glide.RequestManager
import com.wlkmultimedia.R
import java.util.ArrayList

class MediaRecyclerAdapter(private val mediaObjects: ArrayList<MediaObject>,
                           private val requestManager: RequestManager) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        return PlayerViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.feed_list_item, viewGroup, false)
        )
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        (viewHolder as PlayerViewHolder).onBind(mediaObjects[i], requestManager)
    }

    override fun getItemCount(): Int {
        return mediaObjects.size
    }
}
