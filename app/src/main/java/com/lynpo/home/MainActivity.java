package com.lynpo.home;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lynpo.R;
import com.lynpo.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private static final String DEBUG_INFO = "debug_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main_1);
//        ConstraintLayout constraintLayout = findViewById(R.id.parent);
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
//        StarView starView = findViewById(R.id.starView);
        TextView textView = findViewById(R.id.tv);
        int linearLayouttop = linearLayout.getTop();
        int linearLayoutleft = linearLayout.getLeft();

//        int top = starView.getTop();
//        int left = starView.getLeft();
//        float x = starView.getX();
//        float y = starView.getY();

        int textView_top = textView.getTop();
        int textView_left = textView.getLeft();
        float textView_x = textView.getX();
        float textView_y = textView.getY();

//        ViewParent parent = starView.getParent();
//        Log.d(DEBUG_INFO, "constraintLayout.hash:" + constraintLayout.hashCode());
        Log.d(DEBUG_INFO, "linearLayout.hash:" + linearLayout.hashCode());
//        Log.d(DEBUG_INFO, "starView.parent.hash:" + parent.hashCode());
        Log.d(DEBUG_INFO, "linearLayouttop:" + linearLayouttop + ", linearLayoutleft:" + linearLayoutleft);
//        Log.d(DEBUG_INFO, "top:" + top + ", left:" + left + ", x:" + x + ", y:" + y);
        Log.d(DEBUG_INFO, "textView_top:" + textView_top +
                ", textView_left:" + textView_left +
                ", textView_x:" + textView_x +
                ", textView_y:" + textView_y);

//        VideoCapturer c = VideoCapturer.create(device);
//        PeerConnectionFactory factory = new PeerConnectionFactory();
//        MediaStream localStream = factory.createLocalMediaStream(streamName);
//        VideoSource s = factory.createVideoSource(c, constraints);
//        VideoTrack t = factory.createVideoTrack(trackName, s);
//        localStream.addTrack(t);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float rawX = event.getRawX();
        float rawY = event.getRawY();
        Log.d(DEBUG_INFO, "event-x:" + x + ", event-y:" + y + ", rawX:" + rawX + ", rawY:" + rawY);
        return super.onTouchEvent(event);
    }
}
