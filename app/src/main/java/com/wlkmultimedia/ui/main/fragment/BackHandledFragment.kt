package com.wlkmultimedia.ui.main.fragment
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

abstract class BackHandledFragment : Fragment() {
    protected lateinit var backHandlerInterface: BackHandlerInterface

    abstract fun onBackPressed(): Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity !is BackHandlerInterface) {
            throw ClassCastException(
                "Hosting activity must implement BackHandlerInterface"
            )
        } else {
            backHandlerInterface = activity as BackHandlerInterface
        }
    }

    override fun onStart() {
        super.onStart()

        // Mark this fragment as the selected Fragment.
        backHandlerInterface.setmSelectedFragment(this)
    }

    fun setSupportActionBar(toolbar: Toolbar) {
        val compatActivity = activity as AppCompatActivity
        compatActivity.setSupportActionBar(toolbar)

        val actionBar = compatActivity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)

    }

    fun setStatusBarColor(color: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity!!.window
            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            // finally change the color

            window.statusBarColor = Color.parseColor(color)
        }
    }



    fun showActionBar() {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.show()
    }

    fun hideActionBar() {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.hide()
    }

    interface BackHandlerInterface {
        fun setmSelectedFragment(backHandledFragment: BackHandledFragment)
    }

    interface OnTitleTextClickListener {
        fun onTitleTextClicked()
    }
}
