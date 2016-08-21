package com.leavessilent.driverhomenews.adapters;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.leavessilent.driverhomenews.R;
import com.leavessilent.driverhomenews.common.Urls;
import com.leavessilent.driverhomenews.entity.Comment;
import com.leavessilent.mylibrary.adapter.SingleBaseAdapter;

import java.util.List;

/**
 * Created by Leavessilent on 2016/8/21.
 */
public class CommentAdapter extends SingleBaseAdapter<Comment> {
    private int postion;

    public CommentAdapter(Context context, List<Comment> data, int layoutId) {
        super(context, data, layoutId);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.postion = position;
        return super.getView(position, convertView, parent);
    }

    @Override
    protected void bindData(ViewHolder holder, Comment item) {
        TextView idText = ((TextView) holder.getView(R.id.list_comment_id));
        TextView title = ((TextView) holder.getView(R.id.list_comment_title));
        WebView content = ((WebView) holder.getView(R.id.list_comment_content));
        TextView username = ((TextView) holder.getView(R.id.list_comment_username));
        TextView opinion = ((TextView) holder.getView(R.id.list_comment_opinion));
        idText.setText(String.valueOf(postion + 1));
        title.setText(Html.fromHtml("<a href=\"" + Urls.getNewsDetailUrl(item.getId()) + "\">" + item.getSimtitle() + "</a>"));
        content.loadDataWithBaseURL(null, item.getContent(), "text/html", "utf-8", null);
        username.setText(item.getUsername() + " | " + item.getPostdate());
        opinion.setText("支持[" + item.getSupport() + "] 反对[" + item.getOppose() + "]");
    }
}
