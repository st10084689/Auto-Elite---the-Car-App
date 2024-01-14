package com.harmless.autoelitekotlin.view.activities

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.databinding.ActivityMainBinding
import com.harmless.autoelitekotlin.model.User
import com.harmless.autoelitekotlin.view.adapters.VehicleListReyclerAdapter
import com.harmless.autoelitekotlin.view.adapters.ViewPagerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {
    private lateinit var navigationView: NavigationView
    private lateinit var navigationButton: ImageButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var tabLayout: TabLayout

    private lateinit var binding:ActivityMainBinding

    companion object{

        const val TAG = "MainActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        //sets up the tab layout

        //setting the navigation layout ( still using the old non view binding)
        drawerLayout = findViewById(R.id.my_drawer_layout)
        navigationView = findViewById(R.id.navigation_menu)
        navigationButton = findViewById(R.id.NavButton)


        setFragment()

        getUserData()

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
        var tab: TabLayout.Tab? = tabLayout.getTabAt(position!!)

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

    private fun getUserData() {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val uid = currentUser?.uid

        val database = Firebase.database
        val myRef = database.getReference("users").child(uid!!)

        val storage = FirebaseStorage.getInstance()
        val storageReference = storage.reference

        Log.d(TAG, "getUserData: $uid")

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)

                if (user != null) {
                    Log.d(TAG, "onDataChange: The user name is ${user.displayName}")
                    binding.LoginUsersName.text = user.displayName

                    val imageRef = storageReference.child("usersImage/${user.image}")

                    Log.d(TAG, "onDataChange: The user image is ${user.image}")

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val uri = imageRef.downloadUrl.await()
                            withContext(Dispatchers.Main) {
                                Log.d(TAG, "onBindViewHolder: Success, the URI is $uri")
                                Glide.with(binding.loginImage)
                                    .load(uri)
                                    .centerCrop()
                                    .into(binding.loginImage)
                            }
                        } catch (exception: Exception) {
                            Log.d(TAG, "onBindViewHolder: $exception")
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event if needed
            }
        })
    }

}