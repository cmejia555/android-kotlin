package com.cmejia.kotlinapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cmejia.kotlinapp.fragments.DetailsFragment
import com.cmejia.kotlinapp.fragments.GalleryFragment


class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> DetailsFragment()
            1 -> GalleryFragment()
            else -> DetailsFragment()
        }
    }

    override fun getItemCount(): Int {
        return TAB_LAYOUT_COUNT
    }

    companion object {
        private const val TAB_LAYOUT_COUNT = 2
    }

}