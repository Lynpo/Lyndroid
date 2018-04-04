package com.lynpo.nestedfragments

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.lynpo.R

class FragmentContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, ContainerFragment.newInstance())
                .commitAllowingStateLoss()
    }
}
