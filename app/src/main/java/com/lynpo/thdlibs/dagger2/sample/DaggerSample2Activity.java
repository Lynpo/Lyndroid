package com.lynpo.thdlibs.dagger2.sample;

import android.os.Bundle;
import android.widget.TextView;

import com.lynpo.R;
import com.lynpo.eternal.base.ui.BaseDaggerActivity;

import javax.inject.Inject;

public class DaggerSample2Activity extends BaseDaggerActivity {

    @Inject
    String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample2);

        ((TextView) findViewById(R.id.tvName)).setText(className);
    }
}
