package com.lynpo.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lynpo.R;

import androidx.appcompat.app.AppCompatActivity;

public class CustomViewActivity extends AppCompatActivity {

    private ListView mListView1;
    private ListView mListView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_custom_view);
        setContentView(R.layout.layout_horizontal_view);

        mListView1 = findViewById(R.id.lv_one);
        mListView2 = findViewById(R.id.lv_two);

        String[] strs1 = {"1", "2", "3", "4", "5", "6", "7", "2", "2", "2", "2", "2", "2", "2"};
        String[] strs2 = {"A", "B", "3", "4", "5", "6", "7", "2", "2", "2", "2", "2", "2", "2"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, strs1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, strs2);

        mListView1.setAdapter(adapter1);
        mListView2.setAdapter(adapter2);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, CustomViewActivity.class);
        context.startActivity(starter);
    }
}
