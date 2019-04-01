package com.lynpo.androidtouchevent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.lynpo.R

class TouchOrClickActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch_or_click)
    }

    fun clickBottom(view: View) {
        Log.d("debug_info", "Bottom view clicked.")
    }

    fun clickParent(view: View) {
        Log.d("debug_info", "Parent view clicked.")
    }

    fun clickTop(view: View) {
        Log.d("debug_info", "top view clicked.")
    }
}
