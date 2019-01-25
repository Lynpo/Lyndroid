package com.lynpo.daggersample.student;

import android.os.Bundle;
import android.widget.TextView;

import com.lynpo.daggersample.R;
import com.lynpo.daggersample.base.BaseActivity;

import javax.inject.Inject;

//public class ClassActivity extends DaggerAppCompatActivity {
public class ClassActivity extends BaseActivity {

    @Inject
    String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample2);

        ((TextView) findViewById(R.id.tvName)).setText(className);
    }
}
