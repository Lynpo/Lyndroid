package com.lynpo.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lynpo.R;
import com.lynpo.eternal.base.ui.BaseActivity;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new ItemAdapter(new ItemBeanPresenter()));
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
