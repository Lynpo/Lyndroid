package com.lynpo.jni;

import android.os.Bundle;
import android.util.Log;
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
        final TextView signInfo = findViewById(R.id.hashSignatureText);

        /*
         * The signature sha1 is generated with
         * Android Studio - Build > Generate Signed APK > ...
         */
        String javaHashSign = ApkUtil.getCertSHA1(mContext);
        String nativeHashSign = JniVisitor.hashSignFromJNI();
        final String string = JniVisitor.stringFromJNI();
        Log.d("debug_info", "stringFromJNI:" + string);

        signInfo.setText(String.format("javaHashSign:%s\nnativeHashSign:%s", javaHashSign, nativeHashSign));
        textView.postDelayed(() -> textView.setText(string), 1200);
    }
}
