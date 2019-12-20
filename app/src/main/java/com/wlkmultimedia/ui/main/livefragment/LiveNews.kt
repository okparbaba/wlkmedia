package com.wlkmultimedia.ui.main.livefragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.wlkmultimedia.R
import com.wlkmultimedia.ui.main.adapters.liveadapter.AllLiveAdapter
import com.wlkmultimedia.ui.main.adapters.liveadapter.LiveNewsAdapter
import com.wlkmultimedia.ui.main.model.AllLiveObject
import kotlinx.android.synthetic.main.fragment_live_news.*

/**
 * A simple [Fragment] subclass.
 */
class LiveNews : Fragment() {
    private lateinit var items:ArrayList<AllLiveObject>
    private lateinit var adapter: LiveNewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_news, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        items = ArrayList()
        adapter = LiveNewsAdapter(
            fakeLiveData()
        )
        rvLiveNews.layoutManager = LinearLayoutManager(activity)
        rvLiveNews.adapter = adapter
    }
    private fun fakeLiveData():ArrayList<AllLiveObject>{

        val one = AllLiveObject(
            "https://cna-sg-res.cloudinary.com/image/upload/q_auto,f_auto/image/8786976/16x9/991/557/b5725ccd88967cbdbc70aa882ad1c3b5/fk/cna-default-image.png",
            "Channel NewsAsia"
        )
        items.add(one)

        val two = AllLiveObject(
            "https://media.licdn.com/dms/image/C4D0BAQH83-4o_GeDNg/company-logo_200_200/0?e=2159024400&v=beta&t=9goe6fQcfpDcQzilJ4_gTWU5z_MG8QkT9Wem8Lg_ZXY",
            "CGTN"
        )
        items.add(two)

        val three = AllLiveObject(
            "https://assets.bwbx.io/s3/javelin/public/javelin/images/social-default-a4f15fa7ee.jpg",
            "Bloomberg"
        )
        items.add(three)

        val four = AllLiveObject(
            "https://i1.sndcdn.com/avatars-000709704967-ud6ehk-t500x500.jpg",
            "DW English"
        )
        items.add(four)

        val five = AllLiveObject(
            "https://upload.wikimedia.org/wikipedia/en/thumb/6/65/FRANCE_24_logo.svg/1200px-FRANCE_24_logo.svg.png",
            "France 24(English)"
        )
        items.add(five)
        val six = AllLiveObject(
            "https://pbs.twimg.com/profile_images/604916087180754945/1aMHMBIj_400x400.png",
            "TRT World"
        )
        items.add(six)

        val seven = AllLiveObject(
            "https://yt3.ggpht.com/a/AGF-l7--MpQzq3dWlqmcRKqLTgSh2ARlo4G3MgTW=s900-c-k-c0xffffffff-no-rj-mo",
            "Arirang TV"
        )

        return items

    }

}
