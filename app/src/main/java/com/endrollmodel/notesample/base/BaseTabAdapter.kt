package com.endrollmodel.notesample.base

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * tabLayout adapter
 */
class BaseTabAdapter(fragment : Fragment, private val dataList : List<Fragment>) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun createFragment(position: Int): Fragment {
        return dataList[position]
    }
}