package com.lynpo.daggersample.student;

import android.os.Bundle;
import android.widget.TextView;

import com.lynpo.daggersample.R;
import com.lynpo.daggersample.base.BaseDaggerActivity;

import javax.inject.Inject;

public class ClassActivity extends BaseDaggerActivity {

    @Inject
    String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample2);

        ((TextView) findViewById(R.id.tvName)).setText(className);
    }
}
