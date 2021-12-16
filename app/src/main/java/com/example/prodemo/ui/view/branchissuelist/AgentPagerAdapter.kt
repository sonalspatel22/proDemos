package com.example.prodemo.ui.view.branchissuelist

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class AgentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments: ArrayList<Fragment> = ArrayList()
    private val titles: ArrayList<String> = ArrayList()

    override fun getPageTitle(position: Int): CharSequence = titles[position]

    fun add(fragment: Fragment, title: String) {
        fragments.add(fragment)
        titles.add(title)
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    fun getFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    fun setTitle(it: Int?) {
        titles[1]  = "issues($it)"
        notifyDataSetChanged()
    }
}