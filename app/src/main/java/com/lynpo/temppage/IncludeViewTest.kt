package com.lynpo.temppage

import android.os.Bundle
import com.lynpo.R
import com.lynpo.R.id.*
import com.lynpo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_include_a_view.*
import kotlinx.android.synthetic.main.view_text.view.*


/**
 * Create by fujw on 2018/4/21.
 **
 * IncludeViewTest
 */
class IncludeViewTest : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_include_a_view)

        button.setOnClickListener {

        }
        text_container.button_text.text = getString(R.string.str_kidding)

    }
}