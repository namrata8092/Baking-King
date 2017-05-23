package com.nds.baking.king.views.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nds.baking.king.R;

/**
 * Created by Namrata Shah on 5/8/2017.
 */

public class RecipeVideoFragment extends Fragment {

    private static final String KEY_VIDEO_URL="videoUrl";
    private String videoURL;
    public static RecipeVideoFragment newInstance(String url){
        RecipeVideoFragment fragment = new RecipeVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_VIDEO_URL,url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_video_fragment, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            videoURL = getArguments().getString(KEY_VIDEO_URL);
        }
    }
}
