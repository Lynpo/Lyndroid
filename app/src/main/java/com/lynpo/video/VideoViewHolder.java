package com.lynpo.video;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lynpo.R;

import cn.jzvd.JZUserAction;
import cn.jzvd.JZVideoPlayerStandard;


/**
 * Create by fujw on 2018/6/8.
 * *
 * VideoViewHolder
 */
public class VideoViewHolder extends RecyclerView.ViewHolder {

    private JZVideoPlayerStandard mPlayer;

    public VideoViewHolder(View itemView) {
        super(itemView);
        mPlayer = itemView.findViewById(R.id.videoPlayerStandard);
    }

    public void bindData(VideoBean item) {
        mPlayer.setUp(item.url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
        mPlayer.startVideo();
        mPlayer.onEvent(JZUserAction.ON_CLICK_START_ICON);
    }
}
