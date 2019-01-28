package com.lynpo.detail;

import android.os.Bundle;

import com.google.gson.Gson;
import com.lynpo.LynpoApp;
import com.lynpo.R;
import com.lynpo.eternal.base.ui.BaseActivity;
import com.lynpo.thdlibs.dagger2.model.Swordman;

import javax.inject.Inject;

import dagger.Lazy;

public class DetailActivity extends BaseActivity {

    @Inject
    Gson gson;
    @Inject
    Gson gson1;
    // 懒加载
    @Inject
    Lazy<Swordman> swordmanLazy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        DaggerActivityComponent
        LynpoApp.get(this).getActivityComponent().inject(this);

        Swordman swordman = swordmanLazy.get();
    }
}
