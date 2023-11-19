package com.harmless.autoelitekotlin.view

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.harmless.autoelitekotlin.R

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {
    private lateinit var navigationView: NavigationView
    private lateinit var navigationButton: ImageButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var tabLayout: TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init(){
        //sets up the tab layout

        //setting the navigation layout
        drawerLayout = findViewById(R.id.my_drawer_layout)
        navigationView = findViewById(R.id.navigation_menu)
        navigationButton = findViewById(R.id.NavButton)

        setFragment()

    }

    private fun setFragment(){
        tabLayout = findViewById(R.id.tabLayout)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.addOnTabSelectedListener(this)
        updateTabStyle(0)
    }

    private fun updateTabStyle(position:Int?){
        var tab:TabLayout.Tab? = tabLayout.getTabAt(position!!)

        if(tab != null){
            var color:Int = getFragmentThemeColor(position)
            tabLayout.setSelectedTabIndicatorColor(color)
        }
    }

    private fun getFragmentThemeColor(position:Int):Int{
        when(position){
            0->{
                return resources.getColor(R.color.car_screen_color)
            }
            1->{
                return resources.getColor(R.color.motorbike_screen_color)
            }
            2->{
                return resources.getColor(R.color.bike_screen_color)
            }
            3->{
                return resources.getColor(R.color.accessories_screen_color)
            }
            else ->{
                return resources.getColor(R.color.black, theme)
            }
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        var tabPosition = tab?.position
        updateTabStyle(tabPosition)
        val TabLayout =
            (tabLayout.getChildAt(0) as ViewGroup).getChildAt(tab!!.position) as LinearLayout
        val tabTextView = TabLayout.getChildAt(1) as TextView
        tabTextView.setTypeface(tabTextView.typeface, Typeface.BOLD)

        tabTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        val TabLayout =
            (tabLayout.getChildAt(0) as ViewGroup).getChildAt(tab!!.position) as LinearLayout
        val tabTextView = TabLayout.getChildAt(1) as TextView
        tabTextView.setTypeface(null, Typeface.NORMAL)
        tabTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }
}