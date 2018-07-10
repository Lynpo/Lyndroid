package com.lynpo.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.lynpo.R;
import com.lynpo.activitylifecycle.TaskAActivity;
import com.lynpo.base.BaseActivity;
import com.lynpo.base.adapter.BaseRclViewAdapter;
import com.lynpo.base.adapter.CommonViewHolder;
import com.lynpo.common.ClassHolder;
import com.lynpo.jni.JniInvokeActivity;
import com.lynpo.kotlin.ReturnNullActivity;
import com.lynpo.video.VideoActivity;
import com.lynpo.view.CustomViewActivity;
import com.lynpo.view.MoveViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by fujw on 2018/4/1.
 * *
 * HomeActivity
 */
public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);

        GridItemAdapter mAdapter = new GridItemAdapter();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutManager);

        List<ClassHolder> items = new ArrayList<>();
        items.add(new ClassHolder(MainActivity.class));
        items.add(new ClassHolder(TaskAActivity.class));
        items.add(new ClassHolder(MoveViewActivity.class));

        items.add(new ClassHolder(CustomViewActivity.class));
        items.add(new ClassHolder(VideoActivity.class));
        items.add(new ClassHolder(ReturnNullActivity.class));
        items.add(new ClassHolder(JniInvokeActivity.class));

        mAdapter.setData(items);
    }

    public void onGridItemClick(String classCanonicalName) {
        start(mContext, classCanonicalName);
    }

    public static void start(Context context, String targetName) {
        try {
            Intent starter = new Intent(context, Class.forName(targetName));
            if (starter.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(starter);
            }
        } catch (ClassNotFoundException ignored) {
        }
    }

    private class GridItemAdapter extends BaseRclViewAdapter<ClassHolder> {

        GridItemAdapter() {
            super(null, R.layout.layout_home_grid_item);
        }

        @Override
        protected void onItemBindViewHolder(CommonViewHolder holder, int position) {
            final ClassHolder item = getItem(position);
            if (item == null || item.n_class == null) {
                return;
            }

            holder.setText(R.id.textView, item.n_class.getSimpleName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onGridItemClick(item.n_class.getCanonicalName());
                }
            });
        }
    }
}
