package com.nds.baking.king.views;

import android.support.v4.media.session.MediaSessionCompat;

import com.google.android.exoplayer2.SimpleExoPlayer;

/**
 * Created by Namrata Shah on 6/27/2017.
 */

public class MySessionCallback extends MediaSessionCompat.Callback {
    private SimpleExoPlayer mSimpleExpoPlayer;

    public MySessionCallback(SimpleExoPlayer simpleExpoPlayer){
        this.mSimpleExpoPlayer = simpleExpoPlayer;
    }

    @Override
    public void onPlay() {
        mSimpleExpoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
        mSimpleExpoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void onSkipToPrevious() {
        mSimpleExpoPlayer.seekTo(0);
    }

}
