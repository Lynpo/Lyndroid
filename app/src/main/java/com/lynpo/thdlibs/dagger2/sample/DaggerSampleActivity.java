package com.lynpo.thdlibs.dagger2.sample;

import android.os.Bundle;
import android.widget.TextView;

import com.lynpo.R;
import com.lynpo.eternal.base.ui.BasePresenterActivity;

import javax.inject.Inject;

public class DaggerSampleActivity extends BasePresenterActivity<SamplePresenter> implements SampleContract.View {

    @Inject
    String className;
    @Inject
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        ((TextView) findViewById(R.id.tvName)).setText(className);
        String stuName = student.name;
        mPresenter.setName(stuName);
        String cacheName = mPresenter.getName();
        ((TextView) findViewById(R.id.tvStudent)).setText(cacheName);
    }
}
