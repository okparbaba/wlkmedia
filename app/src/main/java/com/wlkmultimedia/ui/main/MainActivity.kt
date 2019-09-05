package com.wlkmultimedia.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.mancj.materialsearchbar.MaterialSearchBar
import com.wlkmultimedia.R
import com.wlkmultimedia.SimpleFragmentPagerAdapter
import com.wlkmultimedia.utils.FontUtil.Companion.isValid
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), View.OnClickListener, MaterialSearchBar.OnSearchActionListener  {
    override fun onButtonClicked(buttonCode: Int) {
        when (buttonCode) {
            MaterialSearchBar.BUTTON_NAVIGATION -> drawer_layout.openDrawer(Gravity.LEFT)
            MaterialSearchBar.BUTTON_SPEECH -> {
            }
            MaterialSearchBar.BUTTON_BACK -> searchBar.disableSearch()
        }
    }

    override fun onSearchStateChanged(enabled: Boolean) {

    }

    override fun onSearchConfirmed(text: CharSequence?) {

    }

    override fun onClick(p0: View?) {

    }

    private lateinit var searchBar:MaterialSearchBar
    private lateinit var toolbar: Toolbar
    internal var searchtollbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentLocale = Locale.getDefault().language
        isValid = if (currentLocale.toString() == "my") {
            setLocale("my")
            true
        } else {
            setLocale("zy")
            false
        }
        val t = ActionBarDrawerToggle(
            this,
            drawer_layout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        searchBar = findViewById(R.id.searchBar)
        searchBar.setOnSearchActionListener(this)
        searchBar.inflateMenu(R.menu.main)
        searchBar.text = ""
        searchBar.setCardViewElevation(10)
        searchBar.addTextChangeListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })
        toolbar = findViewById(R.id.toolbar)
        drawer_layout.addDrawerListener(t)
        t.syncState()
        //setSupportActionBar(toolbar)
        nv.setNavigationItemSelectedListener { p0 ->
            when (p0.itemId) {
                R.id.account -> {
                }
                R.id.settings -> {

                }
                R.id.mycart -> {

                }
            }
            val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        //imgToolbar?.setOnClickListener { drawer_layout.openDrawer(GravityCompat.START) }

        //setSearchtollbar()
        val simpleFragmentPagerAdapter =
            SimpleFragmentPagerAdapter(supportFragmentManager)
        //val viewPager: ViewPager = findViewById(R.id.view_pager)
        view_pager.adapter = simpleFragmentPagerAdapter
        //val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(view_pager)
        for (i in 0 until tabs.tabCount) {
            val tab = (tabs.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(18, 0, 18, 0)
            tab.requestLayout()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return true
    }
    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        val config = baseContext.resources.configuration
        Locale.setDefault(locale)
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun circleReveal(viewID: Int, posFromRight: Int, containsOverflow: Boolean, isShow: Boolean) {
        val myView = findViewById<View>(viewID)

        var width = myView.width

        if (posFromRight > 0)
            width -= posFromRight * resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) - resources.getDimensionPixelSize(
                R.dimen.abc_action_button_min_width_material
            ) / 2
        if (containsOverflow)
            width -= resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material)

        val cx = width
        val cy = myView.height / 2

        val anim: Animator
        if (isShow)
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0f, width.toFloat())
        else
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, width.toFloat(), 0f)

        anim.duration = 220.toLong()

        // make the view invisible when the animation is done
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (!isShow) {
                    super.onAnimationEnd(animation)
                    myView.visibility = View.INVISIBLE
                }
            }
        })

        // make the view visible and start the animation
        if (isShow)
            myView.visibility = View.VISIBLE

        // start the animation
        anim.start()


    }
}
