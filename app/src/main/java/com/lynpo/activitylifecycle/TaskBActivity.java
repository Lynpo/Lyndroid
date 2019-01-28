package com.lynpo.activitylifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lynpo.R;
import com.lynpo.eternal.base.ui.BaseActivity;

public class TaskBActivity extends BaseActivity {

    private static final String TAG = "TaskBActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_b);

        Log.d("debug_info", TAG + "-onCreate");

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, TaskAActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("debug_info", TAG + "-onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("debug_info", TAG + "-onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("debug_info", TAG + "-onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("debug_info", TAG + "-onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("debug_info", TAG + "-onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("debug_info", TAG + "-onDestroy");
    }
}
