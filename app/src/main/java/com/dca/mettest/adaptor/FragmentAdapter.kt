package com.dca.mettest.adaptor

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.dca.mettest.view.HomeFragment

class FragmentAdapter (private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {
    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        val b = Bundle()
        b.putInt("position", position)
        val frag: Fragment = HomeFragment.newInstance(position)
        frag.arguments = b
        return frag
    }

    override fun getCount(): Int {
        return totalTabs
    }
}