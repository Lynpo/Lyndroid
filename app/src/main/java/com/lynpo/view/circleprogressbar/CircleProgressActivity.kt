package com.lynpo.view.circleprogressbar

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.SeekBar
import com.lynpo.R
import com.lynpo.eternal.base.ui.BaseActivity
import com.lynpo.eternal.base.ui.BaseFragment
import com.lynpo.view.circleprogressbar.colorpicker.ColorPickerDialog


class CircleProgressActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_progress)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, PlaceholderFragment())
                    .commit()
        }
    }


//    fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }

//    fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        val id = item.getItemId()
//
//
//        return if (id == R.id.action_settings) {
//            true
//        } else super.onOptionsItemSelected(item)
//
//    }


    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : BaseFragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_main, container, false)
            val seekBarProgress: SeekBar
            val seekBarThickness: SeekBar
            seekBarProgress = rootView.findViewById(R.id.seekBar_progress)
            seekBarThickness = rootView.findViewById(R.id.seekBar_thickness)
            val button = rootView.findViewById(R.id.button) as Button
            val circleProgressBar = rootView.findViewById(R.id.custom_progressBar) as CircleProgressBar
            val progressBar = rootView.findViewById(R.id.progressBar) as ProgressBar
            progressBar.max = 100

            //Using ColorPickerLibrary to pick color for our CustomProgressbar
            val colorPickerDialog = ColorPickerDialog()
            colorPickerDialog.initialize(
                    R.string.select_color,
                    intArrayOf(Color.CYAN, Color.DKGRAY, Color.BLACK, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED, Color.GRAY, Color.YELLOW),
                    Color.DKGRAY, 3, 2)

            colorPickerDialog.setOnColorSelectedListener { color -> circleProgressBar.color = color }
            button.setOnClickListener { colorPickerDialog.show(fragmentManager, "color_picker") }
            seekBarProgress.progress = circleProgressBar.progress.toInt()
            seekBarProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    if (b) {
                        circleProgressBar.setProgressWithAnimation(i.toFloat())
                        progressBar.progress = i
                    }
                    else {
                        circleProgressBar.progress = i.toFloat()
                        progressBar.progress = i
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {

                }
            })
            seekBarThickness.progress = circleProgressBar.strokeWidth.toInt()
            seekBarThickness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    circleProgressBar.strokeWidth = i.toFloat()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {

                }
            })
            return rootView
        }
    }
}
