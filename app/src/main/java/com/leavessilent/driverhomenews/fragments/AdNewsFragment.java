package com.leavessilent.driverhomenews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leavessilent.driverhomenews.R;
import com.leavessilent.driverhomenews.entity.News;
import com.leavessilent.mylibrary.ImageLoader;

public class AdNewsFragment extends Fragment {


    private static final String TAG = AdNewsFragment.class.getSimpleName();

    public AdNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ad_news, container, false);
        Bundle arguments = getArguments();
        News news = arguments.getParcelable("news");
        ImageView imageView = (ImageView) view.findViewById(R.id.fragment_ad_image);
        ImageLoader.display(imageView, news.getIcon(), 320, 200);
        return view;
    }
}
