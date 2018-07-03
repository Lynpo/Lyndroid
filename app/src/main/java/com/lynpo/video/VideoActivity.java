package com.lynpo.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lynpo.R;
import com.lynpo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, VideoActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        RecyclerView recyclerView = findViewById(R.id.videoRecyclerView);
        List<VideoBean> videoBeans = new ArrayList<>();
        VideoBean bean1 = new VideoBean();
        VideoBean bean2 = new VideoBean();
        VideoBean bean3 = new VideoBean();
        VideoBean bean4 = new VideoBean();
        videoBeans.add(bean1);
        videoBeans.add(bean2);
        videoBeans.add(bean3);
        videoBeans.add(bean4);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new VideoAdapter(videoBeans));
    }


}
