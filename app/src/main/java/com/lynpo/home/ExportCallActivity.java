package com.lynpo.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.lynpo.base.BaseActivity;

/**
 * 接收应用外打开跳转
 */
public class ExportCallActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null && getIntent().getAction() != null && getIntent().getAction().equals(Intent.ACTION_VIEW)) {
            handleUri(getIntent().getData());
        }
        finish();
    }

    private void handleUri(Uri uri) {
        Intent send;
        send = new Intent(this, MainActivity.class);
        send.setData(uri);
//        if (LynpoApp.mMainActivityRunning) {
//            send = new Intent(this, HomeActivity.class);
//        } else {
//            send = new Intent(this, MainActivity.class);
//        }
//        send.setData(UrlParseUtil.convertToLynpoUri(uri));
//        send.putExtra(RouterConstants.JUMP_FROM_TYPE, RouterConstants.JUMP_FROM_EXPORT);
        startActivity(send);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getAction() != null && intent.getAction().equals(Intent.ACTION_VIEW)) {
            handleUri(intent.getData());
        }
        finish();
    }

}
