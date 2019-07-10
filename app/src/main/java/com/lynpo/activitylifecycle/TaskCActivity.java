package com.lynpo.activitylifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lynpo.R;
import com.lynpo.eternal.LynConstants;
import com.lynpo.eternal.base.ui.BaseActivity;

public class TaskCActivity extends BaseActivity {

    private static final String TAG = TaskCActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_c);

        Log.d(LynConstants.LOG_TAG, TAG + "-onCreate");

        findViewById(R.id.button).setOnClickListener(v -> {
//            Intent intent = new Intent(mContext, TaskBActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//                startActivity(intent);
                    Intent data = new Intent();
                    data.putExtra("DATA", 1);
                setResult(RESULT_OK, data);
                finish();
            }
        );
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
