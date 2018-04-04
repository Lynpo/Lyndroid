package com.lynpo.nestedfragments


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lynpo.R
import com.lynpo.base.BaseFragment

/**
 * Create by fujw on 2018/4/4.
 * *
 * ContainerFragment
 */
class ContainerFragment : BaseFragment() {

    lateinit var mTabLayout: TabLayout
    lateinit var mViewPager: ViewPager

    private val mTitle = arrayOf("FIRST", "SECOND")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pager, container, false)
        mTabLayout = view.findViewById(R.id.tabLayout)
        mViewPager = view.findViewById(R.id.viewPager)

        val adapter = InnerPagerAdapter(childFragmentManager)
        mViewPager.adapter = adapter
        mTabLayout.setupWithViewPager(mViewPager)
        return view
    }

    private inner class InnerPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getPageTitle(position: Int): CharSequence? {
            return mTitle[position]
        }

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return InnerFragment.newInstance()
                1 -> return InnerFragment.newInstance()
            }
            return null
        }

        override fun getCount(): Int {
            return 2
        }
    }

    companion object {

        fun newInstance(): ContainerFragment {

            val args = Bundle()

            val fragment = ContainerFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
