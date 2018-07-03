package com.lynpo.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.lynpo.R;
import com.lynpo.activitylifecycle.TaskAActivity;
import com.lynpo.base.BaseActivity;
import com.lynpo.kotlin.ReturnNullActivity;
import com.lynpo.video.VideoActivity;
import com.lynpo.view.CustomViewActivity;
import com.lynpo.view.MoveViewActivity;

/**
 * Create by fujw on 2018/4/1.
 * *
 * HomeActivity
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView tv11 = findViewById(R.id.tv_11);
        TextView tv12 = findViewById(R.id.tv_12);
        TextView tv13 = findViewById(R.id.tv_13);

        TextView tv21 = findViewById(R.id.tv_21);
        TextView tv22 = findViewById(R.id.tv_22);
        TextView tv23 = findViewById(R.id.tv_23);

        tv11.setText("Main");
        tv12.setText("TaskA");
        tv13.setText("MoveView");

        tv21.setText("CustomView");
        tv22.setText("Video");
        tv23.setText("KotlinReturnNull");

        tv11.setOnClickListener(this);
        tv12.setOnClickListener(this);
        tv13.setOnClickListener(this);
        tv21.setOnClickListener(this);
        tv22.setOnClickListener(this);
        tv23.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_11:
                MainActivity.start(mContext);
                break;
            case R.id.tv_12:
                TaskAActivity.start(mContext);
                break;
            case R.id.tv_13:
                MoveViewActivity.start(mContext);
                break;
            case R.id.tv_21:
                CustomViewActivity.start(mContext);
                break;
            case R.id.tv_22:
                VideoActivity.start(mContext);
                break;
            case R.id.tv_23:
//                startActivity(ReturnNullActivity.Companion.returnNullIntent(mContext, 1));
//                startActivity(ReturnNullActivity.returnNullIntent(mContext, 1));
                ReturnNullActivity.start(mContext);
                break;
        }
    }
}
