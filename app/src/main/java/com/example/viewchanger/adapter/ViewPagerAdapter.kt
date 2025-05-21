package com.example.viewchanger.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.viewchanger.fragments.HomeFragment
import com.example.viewchanger.fragments.SettingsFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2  // We have 2 tabs

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()  // First fragment for first tab
            1 -> SettingsFragment()  // Second fragment for second tab
            else -> throw IllegalStateException("Invalid position")
        }
    }
}