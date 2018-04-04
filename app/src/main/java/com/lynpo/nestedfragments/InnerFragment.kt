package com.lynpo.nestedfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lynpo.R
import com.lynpo.base.BaseFragment


/**
 * Create by fujw on 2018/4/4.
 * *
 * InnerFragment
 */
class InnerFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_inner_page, container, false)
    }

    companion object {

        fun newInstance(): InnerFragment {

            val args = Bundle()

            val fragment = InnerFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
