package com.wlkmultimedia.ui.main.navfragment


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidwave.exoplayer.model.MediaObject
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.wlkmultimedia.R
import com.wlkmultimedia.ui.main.adapters.feedadapter.DividerItemDecoration
import com.wlkmultimedia.ui.main.adapters.feedadapter.MediaRecyclerAdapter
import com.wlkmultimedia.ui.main.utils.ExoPlayerRecyclerView
import com.wlkmultimedia.ui.main.utils.dump.photoSource
import kotlinx.android.synthetic.main.feed_list_item.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class Feed : Fragment() {
    internal var mRecyclerView: ExoPlayerRecyclerView? = null

    private val mediaObjectList = ArrayList<MediaObject>()
    private var mAdapter: MediaRecyclerAdapter? = null
    private var firstTime = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        // Prepare demo content
        prepareVideoList()

        //set data object
        mRecyclerView!!.setMediaObjects(mediaObjectList)
        mAdapter = MediaRecyclerAdapter(mediaObjectList, initGlide()){
            vh, pos ->
            mRecyclerView!!.playVideo(true)
            vh.itemView.ivPlay.animate().alpha(0f).setDuration(500)
        }

        //Set Adapter
        mRecyclerView!!.adapter = mAdapter

        if (firstTime) {
            Handler(Looper.getMainLooper()).post { mRecyclerView!!.playVideo(false) }
            firstTime = false
        }
    }
    private fun initView() {
        mRecyclerView = view!!.findViewById(R.id.exoPlayerRecyclerView)
        mRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        val dividerDrawable = ContextCompat.getDrawable(activity!!, R.drawable.divider_drawable)
        dividerDrawable?.let { DividerItemDecoration(it) }?.let { mRecyclerView!!.addItemDecoration(it) }
        mRecyclerView!!.itemAnimator = DefaultItemAnimator()
    }

    private fun initGlide(): RequestManager {
        val options = RequestOptions()

        return Glide.with(this)
            .setDefaultRequestOptions(options)
    }
    override fun onDestroy() {
        if (mRecyclerView != null) {
            mRecyclerView!!.releasePlayer()
        }
        super.onDestroy()
    }

    private fun prepareVideoList() {
        val mediaObject = MediaObject()
        mediaObject.id = 1
        mediaObject.userHandle = "Popular"
        mediaObject.title = "Tramping is too easy with all this money. My days were more " +
                "exciting when I was penniless and had to forage around for my next meal..." +
                " freedom and simple beauty of it is just too good to pass up."
        mediaObject.coverUrl = photoSource[0]
        mediaObject.url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4"

        val mediaObject2 = MediaObject()
        mediaObject2.id = 2
        mediaObject2.userHandle = "Something"
        mediaObject2.title = "There are uses to adversity, and they don't reveal themselves until tested." +
                " Whether it's serious illness " +
                "speak limited English, difficulty can tap unexpected strengths."
        mediaObject2.coverUrl = photoSource[1]
        mediaObject2.url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4"

        val mediaObject3 = MediaObject()
        mediaObject3.id = 3
        mediaObject3.userHandle = "Popular"
        mediaObject3.title = "That's been one of my mantras - focus and simplicity." +
                "thinking clean to make it simple. But it's worth it in the end because once you get there, you can move mountains."
        mediaObject3.coverUrl = photoSource[2]
        mediaObject3.url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4"

        val mediaObject4 = MediaObject()
        mediaObject4.id = 4
        mediaObject4.userHandle = "Popular"
        mediaObject4.title = "A disruptive innovation is a technologically simple innovation" +
                " in the form of a product, service, or business model that takes root in a tier of the market "
        mediaObject4.coverUrl = photoSource[3]
        mediaObject4.url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4"

        val mediaObject5 = MediaObject()
        mediaObject5.id = 5
        mediaObject5.userHandle = "Popular"
        mediaObject5.title = "Design is the method of putting form and content together. Design, just as art, " +
                "has multiple definitions; there is no single definition. Design can be art. Design can be aesthetics."
        mediaObject5.coverUrl = photoSource[4]
        mediaObject5.url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4"

        mediaObjectList.add(mediaObject)
        mediaObjectList.add(mediaObject2)
        mediaObjectList.add(mediaObject3)
        mediaObjectList.add(mediaObject4)
        mediaObjectList.add(mediaObject5)
        mediaObjectList.add(mediaObject)
        mediaObjectList.add(mediaObject2)
        mediaObjectList.add(mediaObject3)
        mediaObjectList.add(mediaObject4)
        mediaObjectList.add(mediaObject5)
    }
}
