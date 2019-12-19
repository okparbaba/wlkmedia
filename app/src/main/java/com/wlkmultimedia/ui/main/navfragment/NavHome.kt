package com.wlkmultimedia.ui.main.navfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.tabs.TabLayout

import com.wlkmultimedia.R
import com.wlkmultimedia.ui.main.tabsfragment.*
import kotlinx.android.synthetic.main.fragment_nav_home.*

/**
 * A simple [Fragment] subclass.
 */
class NavHome : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFragment(Home())
        tabs.addTab(tabs.newTab().setText("Home"))
        tabs.addTab(tabs.newTab().setText("Korea"))
        tabs.addTab(tabs.newTab().setText("Movies"))
        tabs.addTab(tabs.newTab().setText("News"))
        tabs.addTab(tabs.newTab().setText("TV"))
        tabs.addTab(tabs.newTab().setText("Crime"))
        tabs.addTab(tabs.newTab().setText("Drama"))
        tabs.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{

            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (p0?.position==0){
                    loadFragment(Home())
                }else if(p0?.position==1){
                    loadFragment(Korea())
                }else if (p0?.position==2){
                    loadFragment(Movies())
                }else if (p0?.position==3){
                    loadFragment(News())
                }else if (p0?.position==4){
                    loadFragment(TV())
                }else if (p0?.position==5){
                    loadFragment(Crime())
                }else if (p0?.position==6){
                    loadFragment(Drama())
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
