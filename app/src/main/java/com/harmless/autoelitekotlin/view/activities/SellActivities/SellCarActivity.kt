package com.harmless.autoelitekotlin.view.activities.SellActivities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.databinding.ActivitySellCarBinding
import com.harmless.autoelitekotlin.view.adapters.SellCarViewPagerAdapter

class SellCarActivity : AppCompatActivity() {

    lateinit var binding: ActivitySellCarBinding

    lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySellCarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }

    private fun init(){
         viewPager = binding.sellCarViewpager


        val adapter = SellCarViewPagerAdapter(this)
        viewPager.adapter = adapter

    }

    fun goToNextPage() {
        viewPager.setCurrentItem(viewPager.currentItem + 1, true)
    }

    fun goToPreviousPage() {
        viewPager.setCurrentItem(viewPager.currentItem - 1, true)
    }
}