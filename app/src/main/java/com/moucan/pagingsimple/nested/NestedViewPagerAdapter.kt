package com.moucan.pagingsimple.nested

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class NestedViewPagerAdapter constructor(private val context: FragmentActivity):
    FragmentStateAdapter(context) {
    companion object{
        val titles = arrayListOf("页面1", "页面2", "页面3")
    }

    override fun getItemCount() = titles.size

    override fun createFragment(position: Int): Fragment {
        return ChildFragment()
    }
}