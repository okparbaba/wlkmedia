package com.wlkmultimedia.ui.main.navfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout

import com.wlkmultimedia.R
import com.wlkmultimedia.ui.main.livefragment.*
import com.wlkmultimedia.ui.main.tabsfragment.*
import kotlinx.android.synthetic.main.fragment_live.*

/**
 * A simple [Fragment] subclass.
 */
class Live : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFragment(All())
        tabs.addTab(tabs.newTab().setText("ALL"))
        tabs.addTab(tabs.newTab().setText("NEWS"))
        tabs.addTab(tabs.newTab().setText("SPORTS"))
        tabs.addTab(tabs.newTab().setText("ENTERTAINMENT"))
        tabs.addTab(tabs.newTab().setText("LIFESTYLE"))
        tabs.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{

            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (p0?.position==0){
                    loadFragment(All())
                }else if(p0?.position==1){
                    loadFragment(LiveNews())
                }else if (p0?.position==2){
                    loadFragment(Sports())
                }else if (p0?.position==3){
                    loadFragment(Entertainment())
                }else if (p0?.position==4){
                    loadFragment(LifeStyle())
                }
            }
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }


        })
    }
    private fun loadFragment(fragment: Fragment?): Boolean {
        //switching fragment
        if (fragment != null) {
            activity!!.supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.fragmentContainer, fragment)
                .commit()
            return true
        }
        return false
    }

}
