package com.leavessilent.driverhomenews.adapters;

import android.content.Context;
import android.widget.ImageView;

import com.leavessilent.driverhomenews.R;
import com.leavessilent.driverhomenews.entity.News;
import com.leavessilent.mylibrary.ImageLoader;
import com.leavessilent.mylibrary.adapter.SingleBaseAdapter;

import java.util.List;

/**
 * Created by Leavessilent on 2016/8/21.
 */
public class PictureNewsAdapter extends SingleBaseAdapter<News> {
    public PictureNewsAdapter(Context context, List<News> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    protected void bindData(ViewHolder holder, News item) {
        ImageView bigimg = (ImageView) holder.getView(R.id.item_picture_bigimage);
        ImageView first = (ImageView) holder.getView(R.id.item_picture_first);
        ImageView second = (ImageView) holder.getView(R.id.item_picture_second);
        ImageView third = (ImageView) holder.getView(R.id.item_picture_third);

        String[] urls = item.getSmallimgsrc().split("\\|");
        ImageLoader.display(bigimg, item.getBigimgsrc(), 320, 200);
        ImageLoader.display(first, urls[0], 120, 100);
        ImageLoader.display(second, urls[1], 120, 100);
        ImageLoader.display(third, urls[2], 120, 100);
    }
}
