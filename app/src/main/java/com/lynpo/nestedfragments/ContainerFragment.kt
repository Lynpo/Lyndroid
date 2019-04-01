package com.lynpo.nestedfragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.lynpo.R
import com.lynpo.eternal.base.ui.BaseFragment

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
