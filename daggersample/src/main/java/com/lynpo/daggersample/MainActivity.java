package com.lynpo.daggersample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lynpo.daggersample.student.ClassActivity;
import com.lynpo.daggersample.student.StudentActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button1:
                startActivity(new Intent(this, StudentActivity.class));
                break;
            case R.id.button2:
                startActivity(new Intent(this, ClassActivity.class));
                break;
        }
    }
}
