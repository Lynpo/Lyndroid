package com.lynpo.daggersample.student;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lynpo.daggersample.R;
import com.lynpo.daggersample.base.BaseActivity;
import com.lynpo.daggersample.model.Student;

import javax.inject.Inject;

//public class StudentActivity extends DaggerAppCompatActivity {
public class StudentActivity extends BaseActivity implements View.OnClickListener {

    @Inject
    String className;
    @Inject
    Student student;
    @Inject
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        sayHello();
    }

    private void sayHello() {
        ((TextView) findViewById(R.id.tvName)).setText(className);
        ((TextView) findViewById(R.id.tvStudent)).setText(student.sayHello());

        findViewById(R.id.tvStudent).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvStudent:
                start(this, ClassActivity.class);
                break;
        }
    }
}
