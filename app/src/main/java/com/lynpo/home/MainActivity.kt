package com.lynpo.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import com.lynpo.R
import com.lynpo.eternal.LynConstants
import com.lynpo.eternal.base.ui.BaseActivity
import com.lynpo.flutter.PageRouter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tipText.setText("现在开通优惠 5 折")

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
        val tvTop = hello_world.top
        val tvLeft = hello_world.left
        val tvX = hello_world.x
        val tvY = hello_world.y
        val parent = starView.parent
        Log.d(DEBUG_INFO, "constraintLayout.hash:" + parent.hashCode())
        //        Log.d(DEBUG_INFO, "linearLayout.hash:" + linearLayout.hashCode());
        Log.d(DEBUG_INFO, "starView.parent.hash:" + parent.hashCode())
        //        Log.d(DEBUG_INFO, "linearLayouttop:" + linearLayouttop + ", linearLayoutleft:" + linearLayoutleft);
        Log.d(DEBUG_INFO, "top:$top, left:$left, x:$x, y:$y")
        Log.d(DEBUG_INFO, "textView_top:" + tvTop +
                ", textView_left:" + tvLeft +
                ", textView_x:" + tvX +
                ", textView_y:" + tvY)

//        VideoCapturer c = VideoCapturer.create(device);
//        PeerConnectionFactory factory = new PeerConnectionFactory();
//        MediaStream localStream = factory.createLocalMediaStream(streamName);
//        VideoSource s = factory.createVideoSource(c, constraints);
//        VideoTrack t = factory.createVideoTrack(trackName, s);
//        localStream.addTrack(t);

        starView.setOnClickListener {
            start(mContext, HomeActivity::class.java.name)
        }


        val params: MutableMap<String, String> = mutableMapOf()
        params["test1"] = "v_test1"
        params["test2"] = "v_test2"
        //Add some params if needed.
        hello_world.setOnClickListener {
            // 冷启动，耗时长
/*
            startActivity(FlutterActivity
                    .withNewEngine()
                    .initialRoute("/lyn_route")
                    .build(this)
            )
*/
            PageRouter.openPageByUrl(this, PageRouter.NATIVE_PAGE_URL, params)
        }

        cornerShape.setOnClickListener {
            // 用 cacheEngine 启动，很快
/*
            startActivity(FlutterActivity
                    .withCachedEngine("lyn_f_engine")
                    .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                    .build(this)
            )
*/
            PageRouter.openPageByUrl(this, PageRouter.FLUTTER_PAGE_URL, params)
        }

        page_entrance.setOnClickListener {
            PageRouter.openPageByUrl(this, PageRouter.FLUTTER_FRAGMENT_PAGE_URL, params)
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