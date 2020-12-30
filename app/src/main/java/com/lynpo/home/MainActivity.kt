package com.lynpo.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.lynpo.R
import com.lynpo.eternal.LynConstants
import com.lynpo.eternal.base.ui.BaseActivity
import com.lynpo.view.shape.TipTextView
import com.lynpo.widget.StarView
import io.flutter.embedding.android.FlutterActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        val constraintLayout = findViewById<ConstraintLayout>(R.id.parent)
        //        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        val tipTextView = findViewById<TipTextView>(R.id.tipText)
        val starView = findViewById<StarView>(R.id.starView)
        val textView = findViewById<TextView>(R.id.tv)
        val editText = findViewById<EditText>(R.id.editText)

        tipTextView.setText("现在开通优惠 5 折")

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                Log.d(LynConstants.LOG_TAG, "beforeTextChanged")
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.d(LynConstants.LOG_TAG, "onTextChanged")
            }

            override fun afterTextChanged(s: Editable) {
                Log.d(LynConstants.LOG_TAG, "afterTextChanged")
            }
        })
        //        int linearLayouttop = linearLayout.getTop();
//        int linearLayoutleft = linearLayout.getLeft();
        val top = starView.top
        val left = starView.left
        val x = starView.x
        val y = starView.y
        val textView_top = textView.top
        val textView_left = textView.left
        val textView_x = textView.x
        val textView_y = textView.y
        val parent = starView.parent
        Log.d(DEBUG_INFO, "constraintLayout.hash:" + constraintLayout.hashCode())
        //        Log.d(DEBUG_INFO, "linearLayout.hash:" + linearLayout.hashCode());
        Log.d(DEBUG_INFO, "starView.parent.hash:" + parent.hashCode())
        //        Log.d(DEBUG_INFO, "linearLayouttop:" + linearLayouttop + ", linearLayoutleft:" + linearLayoutleft);
        Log.d(DEBUG_INFO, "top:$top, left:$left, x:$x, y:$y")
        Log.d(DEBUG_INFO, "textView_top:" + textView_top +
                ", textView_left:" + textView_left +
                ", textView_x:" + textView_x +
                ", textView_y:" + textView_y)

//        VideoCapturer c = VideoCapturer.create(device);
//        PeerConnectionFactory factory = new PeerConnectionFactory();
//        MediaStream localStream = factory.createLocalMediaStream(streamName);
//        VideoSource s = factory.createVideoSource(c, constraints);
//        VideoTrack t = factory.createVideoTrack(trackName, s);
//        localStream.addTrack(t);
        starView.setOnClickListener(this)
        textView.setOnClickListener(this)

        cornerShape.setOnClickListener {
            startActivity(
                    FlutterActivity
                            .withNewEngine()
                            .initialRoute("/lyn_route")
                            .build(this)
            )
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv, R.id.starView -> start(mContext, HomeActivity::class.java.name)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        val rawX = event.rawX
        val rawY = event.rawY
        Log.d(DEBUG_INFO, "event-x:$x, event-y:$y, rawX:$rawX, rawY:$rawY")
        return super.onTouchEvent(event)
    }

    companion object {
        private const val DEBUG_INFO = "debug_info"
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }
}