package com.lynpo.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.google.gson.Gson;
import com.lynpo.R;
import com.lynpo.base.BaseActivity;

import javax.inject.Inject;

/**
 * Create by fujw on 2018/4/1.
 * *
 * HomeActivity
 */
public class HomeActivity extends BaseActivity {

    private Button btn_jump;
    @Inject
    Gson gson;
    @Inject
    Gson gson1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}
