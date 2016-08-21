package com.leavessilent.driverhomenews.adapters;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.leavessilent.driverhomenews.R;
import com.leavessilent.driverhomenews.entity.News;
import com.leavessilent.mylibrary.ImageLoader;
import com.leavessilent.mylibrary.adapter.SingleBaseAdapter;

import java.util.List;

/**
 * Created by Leavessilent on 2016/8/20.
 */
public class NewsAdapter extends SingleBaseAdapter<News> {
    private static final String TAG = NewsAdapter.class.getSimpleName();

    public NewsAdapter(Context context, List<News> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    protected void bindData(ViewHolder holder, News item) {
        ImageView imageView = (ImageView) holder.getView(R.id.list_news_image);
        TextView title = (TextView) holder.getView(R.id.list_news_title);
        TextView editor = (TextView) holder.getView(R.id.list_news_editor);
        ImageLoader.display(imageView, item.getIcon(), 100, 75);
        title.setText(item.getTitle());
        editor.setText(item.getEditor() + " | " + item.getPostdate());
    }
}
