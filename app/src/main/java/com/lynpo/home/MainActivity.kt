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
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.lynpo.R
import com.lynpo.eternal.LynConstants
import com.lynpo.eternal.base.ui.BaseActivity
import com.lynpo.eternal.extend.dp2Pixel
import com.lynpo.eternal.extend.text
import com.lynpo.flutter.FlutterSpringboardActivity
import com.lynpo.view.shape.*
import com.lynpo.widget.StarView
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

        addTriangleView()
        setupBubbleView()

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
            // https://flutter.dev/docs/development/add-to-app/android/add-flutter-screen?tab=prewarm-engine-kotlin-tab
//            startActivity(
//                    // start with new engine
////                    FlutterActivity
////                            .withNewEngine()
////                            .initialRoute("/lyn_route")
////                            .build(this)
//
//                    // start with cached engine
//                    // When using the withCachedEngine() factory method, pass the same ID that you
//                    // used when caching the desired FlutterEngine.
//                    FlutterActivity.withCachedEngine("1")
//                            // To launch your FlutterActivity with a transparent background, pass
//                            // the appropriate BackgroundMode to the IntentBuilder:
//                            .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
//                            .build(this)
//            )
            start(mContext, FlutterSpringboardActivity::class.java.name)
        }
    }

    private fun setupBubbleView() {
//        val bubbleView = bubbleView.inflate()
        val text = TextView(this)
        val text2 = TextView(this)
        text.text("点击进入电影院观看")
        text2.text("点击进入电影院观看")
        val bubbleView = BubbleLayout.create(this)
            .setBubbleView(text,
                paddingHorizontal = dp2Pixel(dp = 8),
                paddingVertical = dp2Pixel(4)
            )
            .setBubbleCornerRadius(dp2Pixel(8))
            .setBubbleColorRes(R.color.colorAccent)
            .build()
        fl_bubble.addView(bubbleView)

        BubbleBuilder.create(this)
            .setBubbleView(text2,
                paddingHorizontal = dp2Pixel(dp = 8),
                paddingVertical = dp2Pixel(4)
            )
            .setBubbleCornerRadius(dp2Pixel(8))
            .setBubbleColorRes(R.color.colorAccent)
            .setBubbleArrowHorizontalOffset(0, dp2Pixel(20))
            .buildInto(
                fl_bubble2
//                FrameLayout.LayoutParams(
//                    FrameLayout.LayoutParams.WRAP_CONTENT,
//                    FrameLayout.LayoutParams.WRAP_CONTENT
//                )
            )
    }

    private fun addTriangleView() {
        val t1 = TriangleView(this)
        val t2 = TriangleView(this, DuiConfigs.Direction.UP, R.color.colorAccent)
        val t3 = TriangleView(this, DuiConfigs.Direction.LEFT, R.color.colorAccent)
        val t4 = TriangleView(this, DuiConfigs.Direction.RIGHT, R.color.colorAccent)
        val lpH = LinearLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.dp_60), resources.getDimensionPixelSize(R.dimen.dp_30))
        val lpV = LinearLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.dp_30), resources.getDimensionPixelSize(R.dimen.dp_60))
        lpH.marginStart = resources.getDimensionPixelSize(R.dimen.dp_14)
        lpV.marginStart = resources.getDimensionPixelSize(R.dimen.dp_14)
        ll.addView(t1, lpH)
        ll.addView(t2, lpH)
        ll.addView(t3, lpV)
        ll.addView(t4, lpV)
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