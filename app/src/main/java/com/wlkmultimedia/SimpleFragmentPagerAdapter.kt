package com.wlkmultimedia

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wlkmultimedia.ui.main.tabsfragment.*

class SimpleFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    // This determines the fragment for each tab
    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            Home()
        } else if(position == 1) {
            TV()
        }else if(position == 2){
            Movies()
        }else if(position == 3){
            News()
        }else{
            Korea()
        }
    }

    // This determines the number of tabs
    override fun getCount(): Int {
        return 5
    }

//    // This determines the title for each tab
    override fun getPageTitle(position: Int): CharSequence? {
        // Generate title based on item position
        return when (position) {
            0 -> "Home"
            1 -> "TV"
            2 -> "Movies"
            3 -> "News"
            4 -> "Korea"
            else -> null
        }
    }

}