package com.lynpo.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

import com.lynpo.R;
import com.lynpo.activitylifecycle.TaskAActivity;
import com.lynpo.base.BaseActivity;
import com.lynpo.widget.StarView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String DEBUG_INFO = "debug_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ConstraintLayout constraintLayout = findViewById(R.id.parent);
//        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        StarView starView = findViewById(R.id.starView);
        TextView textView = findViewById(R.id.tv);
//        int linearLayouttop = linearLayout.getTop();
//        int linearLayoutleft = linearLayout.getLeft();

        int top = starView.getTop();
        int left = starView.getLeft();
        float x = starView.getX();
        float y = starView.getY();

        int textView_top = textView.getTop();
        int textView_left = textView.getLeft();
        float textView_x = textView.getX();
        float textView_y = textView.getY();

        ViewParent parent = starView.getParent();
        Log.d(DEBUG_INFO, "constraintLayout.hash:" + constraintLayout.hashCode());
//        Log.d(DEBUG_INFO, "linearLayout.hash:" + linearLayout.hashCode());
        Log.d(DEBUG_INFO, "starView.parent.hash:" + parent.hashCode());
//        Log.d(DEBUG_INFO, "linearLayouttop:" + linearLayouttop + ", linearLayoutleft:" + linearLayoutleft);
        Log.d(DEBUG_INFO, "top:" + top + ", left:" + left + ", x:" + x + ", y:" + y);
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

        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv) {
            startActivity(new Intent(mContext, TaskAActivity.class));
//            startActivity(new Intent(mContext, IncludeViewTest.class));
        }
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
