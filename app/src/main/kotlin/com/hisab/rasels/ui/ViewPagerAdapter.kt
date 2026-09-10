package com.hisab.rasels.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hisab.rasels.ui.fragments.HomeFragment
import com.hisab.rasels.ui.fragments.ReportsFragment
import com.hisab.rasels.ui.fragments.SettingsFragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> HomeFragment()
        1 -> ReportsFragment()
        2 -> SettingsFragment()
        else -> HomeFragment()
    }
}