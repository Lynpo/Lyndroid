package com.lynpo.daggersample;

import android.os.Bundle;
import android.view.View;

import com.lynpo.daggersample.base.BaseActivity;
import com.lynpo.daggersample.student.ClassActivity;
import com.lynpo.daggersample.student.NonDIActivity;
import com.lynpo.daggersample.student.StudentActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button1:
                start(mContext, StudentActivity.class);
                break;
            case R.id.button2:
                start(mContext, ClassActivity.class);
                break;
            case R.id.button3:
                start(mContext, NonDIActivity.class);
                break;
        }
    }
}
