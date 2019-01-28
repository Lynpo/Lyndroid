package com.lynpo.view.circleprogressbar

import android.os.Bundle
import com.lynpo.R
import com.lynpo.eternal.base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_drawable_circle_progress.*

class DrawableCircleProgressActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawable_circle_progress)

        progressBar.max = 100
        progressBar.progress = 0
    }

    override fun onResume() {
        super.onResume()

        Thread(Runnable {
            var i = 0
            while (i++ <= 10) {
                Thread.sleep(500)
                runOnUiThread {
                    progressBar.progress = 30 + 5 * i
                }
            }
        }).start()
    }
}
