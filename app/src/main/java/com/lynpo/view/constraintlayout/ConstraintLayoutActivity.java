package com.lynpo.view.constraintlayout;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lynpo.R;

public class ConstraintLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_layout);

        ImageView back = findViewById(R.id.leftIcon);
        ImageView more = findViewById(R.id.firstIconFromRight);
        final ImageView share = findViewById(R.id.secondIconFromRight);
        final ImageView favorite = findViewById(R.id.thirdIconFromRight);

        final TextView title = findViewById(R.id.title);

//        String titleText = ": today is friday ConstraintLayoutActivity";
//        title.setText(titleText);
        more.setImageResource(R.drawable.baseline_more_vert_black_24);
        share.setImageResource(R.drawable.baseline_share_black_24);
        favorite.setImageResource(R.drawable.baseline_favorite_black_24);
        more.setVisibility(View.VISIBLE);
        share.setVisibility(View.VISIBLE);
        favorite.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1200);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String titleText = ": today is friday";
                            title.setText(titleText);
                        }
                    });
                    Thread.sleep(1200);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            favorite.setVisibility(View.GONE);
                        }
                    });
                    Thread.sleep(1200);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String titleText = ": today is friday ConstraintLayoutActivity";
                            title.setText(titleText);
                        }
                    });
                    Thread.sleep(1200);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            share.setVisibility(View.GONE);
                        }
                    });
                } catch (InterruptedException ignored) {

                }
            }
        }).start();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ConstraintLayoutActivity.class);
        starter.putExtra("id", 1);
        context.startActivity(starter);
    }
}
