package com.lynpo.jni;

import android.os.Bundle;
import android.widget.TextView;

import com.lynpo.R;
import com.lynpo.annos.ActivityConfig;
import com.lynpo.base.BaseActivity;
import com.lynpo.util.ApkUtil;

@ActivityConfig(name = "JniInvokeActivity")
public class JniInvokeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni_invoke);

        final TextView textView = findViewById(R.id.jniTextView);
        String hashSign = ApkUtil.getCertSHA1(mContext);
        final String string = JniVisitor.stringFromJNI(mContext, hashSign);

        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText(string);
            }
        }, 1200);
    }
}
