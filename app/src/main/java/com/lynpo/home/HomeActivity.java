package com.lynpo.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.lynpo.R;
import com.lynpo.activitylifecycle.TaskAActivity;
import com.lynpo.base.BaseActivity;
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
        tv11.setText("Main");
        tv12.setText("TaskA");
        tv13.setText("MoveView");
        tv11.setOnClickListener(this);
        tv12.setOnClickListener(this);
        tv13.setOnClickListener(this);
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
        }
    }
}
