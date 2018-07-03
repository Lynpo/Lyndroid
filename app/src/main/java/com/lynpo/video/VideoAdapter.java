package com.lynpo.video;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lynpo.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Create by fujw on 2018/6/8.
 * *
 * VideoAdapter
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {

    protected List<VideoBean> mData;

    public VideoAdapter(List<VideoBean> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        mData = data;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoBean item = mData.get(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
