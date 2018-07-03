package com.lynpo.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lynpo on 16-4-13.
 *
 * RecyclerView 数据适配器 - 基类
 */
public abstract class BaseRclViewAdapter<T> extends RecyclerView.Adapter {

    protected Context mContext;
    private List<T> mData;
    private int mLayoutResId;

    public BaseRclViewAdapter(List<T> data, int layoutResId) {
        mLayoutResId = layoutResId;
        if (data != null) {
            this.mData = data;
        } else {
            mData = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater mInflater = LayoutInflater.from(mContext);

        View itemView = mInflater.inflate(mLayoutResId, parent, false);
        return new CommonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onItemBindViewHolder(((CommonViewHolder) holder), position);
    }

    protected abstract void onItemBindViewHolder(CommonViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public void refreshData(List<T> data) {
        ArrayList<T> newData = new ArrayList<>();
        newData.addAll(data);

        mData.clear();
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    public void setData(List<T> data){
        mData = data;
        notifyDataSetChanged();
    }

    public void deleteItem(int position){
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(T itemData){
        mData.add(itemData);
        notifyItemInserted(mData.size()-1);
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }
}
