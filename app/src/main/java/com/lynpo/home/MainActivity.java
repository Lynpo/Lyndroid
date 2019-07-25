package com.lynpo.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.lynpo.R;
import com.lynpo.eternal.LynConstants;
import com.lynpo.eternal.base.ui.BaseActivity;
import com.lynpo.view.shape.TipTextView;
import com.lynpo.widget.StarView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String DEBUG_INFO = "debug_info";

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ConstraintLayout constraintLayout = findViewById(R.id.parent);
//        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        TipTextView tipTextView = findViewById(R.id.tipText);
        StarView starView = findViewById(R.id.starView);
        TextView textView = findViewById(R.id.tv);
        EditText editText = findViewById(R.id.editText);

        tipTextView.setText("现在开通优惠 5 折");
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(LynConstants.LOG_TAG, "beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(LynConstants.LOG_TAG, "onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(LynConstants.LOG_TAG, "afterTextChanged");
            }
        });
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

        starView.setOnClickListener(this);
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv:
            case R.id.starView:
                start(mContext, HomeActivity.class.getName());
                break;
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
