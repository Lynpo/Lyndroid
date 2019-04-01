package com.lynpo.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lynpo.R;
import com.lynpo.activitylifecycle.TaskAActivity;
import com.lynpo.androidtouchevent.TouchOrClickActivity;
import com.lynpo.base.adapter.BaseRclViewAdapter;
import com.lynpo.base.adapter.CommonViewHolder;
import com.lynpo.common.ClassHolder;
import com.lynpo.eternal.base.ui.BaseActivity;
import com.lynpo.framework.ipc.IPCClient;
import com.lynpo.jni.JniInvokeActivity;
import com.lynpo.kotlin.ReturnNullActivity;
import com.lynpo.temppage.IncludeViewTest;
import com.lynpo.temppage.SchemaActivity;
import com.lynpo.thdlibs.dagger2.sample.DaggerSample2Activity;
import com.lynpo.thdlibs.dagger2.sample.DaggerSampleActivity;
import com.lynpo.video.VideoActivity;
import com.lynpo.view.CustomViewActivity;
import com.lynpo.view.MoveViewActivity;
import com.lynpo.view.circleprogressbar.CircleProgressActivity;
import com.lynpo.view.circleprogressbar.DrawableCircleProgressActivity;
import com.lynpo.view.constraintlayout.ConstraintLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
        items.add(new ClassHolder(TaskAActivity.class));
        items.add(new ClassHolder(MoveViewActivity.class));

        items.add(new ClassHolder(ConstraintLayoutActivity.class));
        items.add(new ClassHolder(IncludeViewTest.class));
        items.add(new ClassHolder(CustomViewActivity.class));
        items.add(new ClassHolder(VideoActivity.class));
        items.add(new ClassHolder(ReturnNullActivity.class));
        items.add(new ClassHolder(JniInvokeActivity.class));
        items.add(new ClassHolder(TouchOrClickActivity.class));
        items.add(new ClassHolder(CircleProgressActivity.class));
        items.add(new ClassHolder(DrawableCircleProgressActivity.class));
        items.add(new ClassHolder(SchemaActivity.class));
        items.add(new ClassHolder(IPCClient.class));
        items.add(new ClassHolder(DaggerSampleActivity.class));
        items.add(new ClassHolder(DaggerSample2Activity.class));

        mAdapter.setData(items);

        towLongNumCompare();
        modeTest();

        int index, value;
        for (int i = 2; ; i++) {
            if ((value = fibonacci(i)) >= 5000) {
                index = i;
                break;
            }
        }

        Log.d("DEBUG_INFO", "The " + index + "-th of fibonacci array is bigger than 5000, which is " + value);
    }

    /**
     * 斐波那契数列
     * Java通项公式版：
     * reference to: https://zh.wikipedia.org/wiki/%E6%96%90%E6%B3%A2%E9%82%A3%E5%A5%91%E6%95%B0%E5%88%97
     * @param n index
     * @return value
     */
    public int fibonacci(int n){
        if(n<2){
            return n;
        }else {
            return fibonacci(n-1)+fibonacci(n-2);
        }
    }

    /**
     * 斐波那契数列
     * Java语言快捷版：
     * reference to: https://zh.wikipedia.org/wiki/%E6%96%90%E6%B3%A2%E9%82%A3%E5%A5%91%E6%95%B0%E5%88%97
     * @param n index
     * @return value
     */
    public int quicklyFibonacci(int n){
        if(n<2){
            return n;
        }else {
            int[] ans = new int[n];
            ans[0] = 1;
            ans[1] = 2;
            for(int i=2;i<n;i++) {
                ans[i]=ans[i-1]+ans[i-2];
            }
            return ans[n-1];
        }
    }

    private void modeTest() {
        Log.d("DEBUG_INFO", "0 % 30 = " + (0 % 30));
    }

    private void towLongNumCompare() {
        long a = 1000;
        long b = 1000;
        if (a > b * 0.95) {
            Log.d("DEBUG_INFO", String.format("%s > %s * 0.95", a, b));
        } else if (a < b * 0.95) {
            Log.d("DEBUG_INFO", String.format("%s < %s * 0.95", a, b));
        } else {
            Log.d("DEBUG_INFO", String.format("%s = %s * 0.95", a, b));
        }
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
            holder.itemView.setOnClickListener(v ->
                    onGridItemClick(item.n_class.getCanonicalName())
            );
        }
    }
}
