package com.lynpo.activitylifecycle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.lynpo.R;
import com.lynpo.eternal.LynConstants;
import com.lynpo.eternal.base.ui.BaseActivity;

public class TaskAActivity extends BaseActivity {

    private static final String TAG = "TaskAActivity";

    public static void start(Context context) {
        Intent starter = new Intent(context, TaskAActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_a);
        Log.d(LynConstants.LOG_TAG, TAG + "-onCreate");

        findViewById(R.id.button).setOnClickListener(v ->
                startActivityForResult(new Intent(mContext, TaskBActivity.class), LynConstants.ReqCode.CODE_1));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LynConstants.LOG_TAG, TAG + "-onActivityResult,requestCode:" + requestCode);
        if (data != null) {
            int p = data.getIntExtra("data", -1);
            Log.d(LynConstants.LOG_TAG, TAG + "-onActivityResult,p:" + p);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(LynConstants.LOG_TAG, TAG + "-onNewIntent");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LynConstants.LOG_TAG, TAG + "-onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LynConstants.LOG_TAG, TAG + "-onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LynConstants.LOG_TAG, TAG + "-onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LynConstants.LOG_TAG, TAG + "-onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LynConstants.LOG_TAG, TAG + "-onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LynConstants.LOG_TAG, TAG + "-onDestroy");
    }
}
