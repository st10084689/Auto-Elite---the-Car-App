package com.harmless.autoelitekotlin.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.harmless.autoelitekotlin.view.fragments.SellOneFragment
import com.harmless.autoelitekotlin.view.fragments.SellThreeFragment
import com.harmless.autoelitekotlin.view.fragments.SellTwoFragment

class SellCarViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragments = listOf(
        SellOneFragment(),
        SellTwoFragment(),
        SellThreeFragment()
    )

    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}