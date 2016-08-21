package com.leavessilent.mylibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Leavessilent on 2016/8/17.
 */
public abstract class SingleBaseAdapter<T> extends BaseAdapter {

    private List<T> mData;
    private LayoutInflater mInflater;
    private int mLayoutId;


    public SingleBaseAdapter(Context context, List<T> data, int layoutId) {
        mInflater = LayoutInflater.from(context);
        if (data == null) {
            mData = new ArrayList<>();
        } else {
            mData = data;
        }
        mLayoutId = layoutId;
    }

    public void addData(List<T> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void updateData(List<T> data) {
        if (data != null)
            mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(mLayoutId, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        bindData(holder, getItem(position));
        return convertView;
    }

    /**
     * 让子类去实现绑定数据方法
     *
     * @param holder
     * @param item
     */
    protected abstract void bindData(ViewHolder holder, T item);

    public static class ViewHolder {
        private View convertView;
        private HashMap<Integer, View> mCacheViews;

        public ViewHolder(View convertView) {
            this.convertView = convertView;
            mCacheViews = new HashMap<>();
        }


        public View getView(int viewId) {
            View view = null;
            if (mCacheViews.containsKey(viewId)) {
                view = mCacheViews.get(viewId);
            } else {
                view = convertView.findViewById(viewId);
                mCacheViews.put(viewId, view);
            }
            return view;
        }

    }
}
