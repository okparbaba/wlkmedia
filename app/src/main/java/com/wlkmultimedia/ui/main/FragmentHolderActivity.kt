package com.wlkmultimedia.ui.main

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.wlkmultimedia.R
import com.wlkmultimedia.ui.main.fragment.BackHandledFragment

class FragmentHolderActivity : AppCompatActivity(), BackHandledFragment.BackHandlerInterface  {
    companion object {
        val EXTRA_DISPLAY_FRAGMENT = "FragmentHolderActivity.EXTRA_DISPLAY_FRAGMENT"
    }

    lateinit var frameLayout: FrameLayout
    var newFragment: Fragment? = null
    private var fragmentName: String? = null
    private var selectedFragment: BackHandledFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_holder)

        setContentView(R.layout.activity_fragment_holder)
        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        fragmentName = intent.getStringExtra(EXTRA_DISPLAY_FRAGMENT)

//        if (ViewAndEditProfile::class.java.simpleName == fragmentName) {
////            newFragment = ViewAndEditProfile()
////        }
////        if (CreatePostSelecting::class.java.simpleName == fragmentName) {
////            newFragment = CreatePostSelecting()
////        }

        if (newFragment != null) {
            val fm = supportFragmentManager
            val fragment = fm.findFragmentById(R.id.main_holderFragment)
            if (fragment == null) {
                fm.beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .add(R.id.main_holderFragment, newFragment!!).commit()
            } else {
                fm.beginTransaction()
                    .replace(R.id.main_holderFragment, newFragment!!).commit()
            }
        } else {
            this@FragmentHolderActivity.finish()
        }
    }

    override fun setmSelectedFragment(backHandledFragment: BackHandledFragment) {
        if (selectedFragment != null) {
        }
        selectedFragment = backHandledFragment
    }
}

/*
ဒီလိုခေါ်ရတယ်
        val intent =
                    Intent(activity?.applicationContext, FragmentHolderActivity::class.java)
                intent.putExtra(
                    FragmentHolderActivity.EXTRA_DISPLAY_FRAGMENT,
                    OtherGallery::class.java.simpleName
                )
                startActivity(intent)
 */