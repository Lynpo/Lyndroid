package com.lynpo.daggersample.student;

import android.os.Bundle;
import android.widget.TextView;

import com.lynpo.daggersample.R;
import com.lynpo.daggersample.base.BaseActivity;

/**
 * An activity without dependency injection,
 * which cannot extends {@link com.lynpo.daggersample.base.BaseDaggerActivity}
 */
public class NonDIActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample2);

        ((TextView) findViewById(R.id.tvName)).setText(NonDIActivity.class.getSimpleName());
    }
}
