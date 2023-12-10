package com.harmless.autoelitekotlin.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.harmless.autoelitekotlin.view.fragments.CarFragment
import com.harmless.autoelitekotlin.view.fragments.FinanceFragment
import com.harmless.autoelitekotlin.view.fragments.SellFragment

class ViewPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3;
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0->{
                return CarFragment()
            }
            1->{
                return SellFragment()
            }
            2->{
                return FinanceFragment()
            }
            else ->{
                return CarFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> {
                return "Search"
            }
            1 -> {
                return "Sell"
            }
            2 -> {
                return "Finance"
            }
        }
        return super.getPageTitle(position)
    }
}