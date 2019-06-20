package com.lynpo.activitylifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lynpo.R;
import com.lynpo.eternal.base.ui.BaseActivity;

public class TaskCActivity extends BaseActivity {

    private static final String TAG = TaskCActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_c);

        Log.d("debug_info", TAG + "-onCreate");

        findViewById(R.id.button).setOnClickListener(v -> {
//                startActivity(new Intent(mContext, TaskBActivity.class))
                    Intent data = new Intent();
                    data.putExtra("DATA", 1);
                setResult(RESULT_OK, data);
                finish();
            }
        );
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
