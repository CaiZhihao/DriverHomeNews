package com.leavessilent.mylibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 加载多布局万能适配器
 * Created by Leavessilent on 2016/8/17.
 */
public abstract class MultipleBaseAdapter<T> extends BaseAdapter {

    private List<T> mData;

    private int[] layoutResIds;

    private LayoutInflater mInflater;

    public MultipleBaseAdapter(Context context, List<T> data, int... layoutResIds) {
        if (data != null) {
            mData = data;
        } else {
            mData = new ArrayList<>();
        }
        this.layoutResIds = layoutResIds;
        mInflater = LayoutInflater.from(context);
    }

    public void addData(List<T> data) {
        if (data != null) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void updataData(List<T> data) {
        if (data != null) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }


    /**
     * type 必须从零开始
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int type = 0;
        // 根据位置获取到对应的数据
        T t = getItem(position);
        // 根据class 获取到数据
        Class<?> cls = t.getClass();
        try {
            // 获取到一个type对应的字段
            Field field = cls.getDeclaredField("type");
            // 设置访问权限
            field.setAccessible(true);
            try {
                //获取到对应的字段值
                type = field.getInt(t);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return type;
    }

    @Override
    public int getViewTypeCount() {
        return layoutResIds.length;
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
            convertView = mInflater.inflate(layoutResIds[getItemViewType(position)], parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        bindData(holder, getItem(position));
        return convertView;
    }

    protected abstract void bindData(ViewHolder holder, T item);


    public static class ViewHolder {
        private View mConvertView;
        private Map<Integer, View> mViewCache;

        public ViewHolder(View convertView) {
            mConvertView = convertView;
            mViewCache = new HashMap<>();
        }

        public View getView(int resId) {
            View view = null;
            if (mViewCache.containsKey(resId)) {
                view = mViewCache.get(resId);
            } else {
                view = mConvertView.findViewById(resId);
                mViewCache.put(resId, view);
            }
            return view;
        }
    }
}
