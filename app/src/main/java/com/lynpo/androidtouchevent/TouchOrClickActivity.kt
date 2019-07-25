package com.lynpo.androidtouchevent

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lynpo.R
import com.lynpo.eternal.LynConstants

class TouchOrClickActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch_or_click)
    }

    fun clickBottom(view: View) {
        Log.d(LynConstants.LOG_TAG, "Bottom view clicked.")
    }

    fun clickParent(view: View) {
        Log.d(LynConstants.LOG_TAG, "Parent view clicked.")
    }

    fun clickTop(view: View) {
        Log.d(LynConstants.LOG_TAG, "top view clicked.")
    }
}
