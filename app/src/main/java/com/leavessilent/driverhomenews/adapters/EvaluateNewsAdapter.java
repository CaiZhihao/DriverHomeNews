package com.leavessilent.driverhomenews.adapters;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.leavessilent.driverhomenews.R;
import com.leavessilent.driverhomenews.entity.News;
import com.leavessilent.mylibrary.ImageLoader;
import com.leavessilent.mylibrary.adapter.SingleBaseAdapter;

import java.util.List;

/**
 * Created by Leavessilent on 2016/8/21.
 */
public class EvaluateNewsAdapter extends SingleBaseAdapter<News> {

    public EvaluateNewsAdapter(Context context, List<News> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    protected void bindData(ViewHolder holder, News item) {
        TextView title = (TextView) holder.getView(R.id.item_evaluate_title);
        TextView desc = (TextView) holder.getView(R.id.item_evaluate_desc);
        ImageView image = (ImageView) holder.getView(R.id.item_evaluate_image);
        title.setText(item.getTitle());
        desc.setText(item.getDesc());
        ImageLoader.display(image, item.getIcon(), 120, 90);
    }
}
