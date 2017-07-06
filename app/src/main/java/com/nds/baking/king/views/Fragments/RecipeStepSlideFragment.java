package com.nds.baking.king.views.Fragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.nds.baking.king.R;
import com.nds.baking.king.models.RecipeStepModel;
import com.nds.baking.king.views.MySessionCallback;
import com.orhanobut.logger.Logger;

/**
 * Created by Namrata Shah on 5/21/2017.
 */

public class RecipeStepSlideFragment extends Fragment implements ExoPlayer.EventListener{
    private static final String TAG = "TEST"+RecipeStepSlideFragment.class.getSimpleName();
    private RecipeStepModel recipeStepModel;
    private static final String BUNDLE_SELECTED_STEP = "selectedStep";
    private SimpleExoPlayerView mSimpleExpoPlayerView;
    private SimpleExoPlayer mSimpleExpoPlayer;
    private String mVideoUrl;
    private String mStepDetail;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    public static RecipeStepSlideFragment newInstance(RecipeStepModel stepModel) {
        RecipeStepSlideFragment fragment = new RecipeStepSlideFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_SELECTED_STEP, stepModel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG,"onCreate");
        if(savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_SELECTED_STEP))
            recipeStepModel = savedInstanceState.getParcelable(BUNDLE_SELECTED_STEP);
        if (getArguments() != null) {
            recipeStepModel = getArguments().getParcelable(BUNDLE_SELECTED_STEP);
        }
        mVideoUrl = recipeStepModel.getVideoURL();
        mStepDetail = recipeStepModel.getDescription();
        Logger.d(TAG," add video to slide");
        Logger.d(TAG,"mVideoUrl "+mVideoUrl+"mRecipeDescription "+mStepDetail);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_detail_slide_fragment, container, false);
        TextView stepsDescriptionTV = (TextView)view.findViewById(R.id.recipeStep);
        mSimpleExpoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.recipeSimpleExpoPlayerView);
        stepsDescriptionTV.setText("");
        stepsDescriptionTV.setText(mStepDetail);
        Logger.d(TAG,"onCreateView");
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Logger.d(TAG,"onSaveInstanceState");
        outState.putParcelable(BUNDLE_SELECTED_STEP, recipeStepModel);
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onStart() {
        super.onStart();
        Logger.d(TAG,"onStart");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            initializeMediaSession();
            initializeExpoPlayer(mVideoUrl);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d(TAG,"onResume");
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M || mSimpleExpoPlayer == null) {
            initializeMediaSession();
            initializeExpoPlayer(mVideoUrl);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d(TAG,"onPause");
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            resetExpoPlayer();
            mMediaSession.setActive(false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d(TAG,"onStop");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            resetExpoPlayer();
            mMediaSession.setActive(false);
        }
    }


    private void initializeMediaSession() {
        Logger.d(TAG,"initializeMediaSession");
        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getActivity(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback(mSimpleExpoPlayer));

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }


    private void initializeExpoPlayer(@NonNull String videoUri) {
        Logger.d(TAG,"initializeExpoPlayer "+videoUri);
        if (mSimpleExpoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mSimpleExpoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mSimpleExpoPlayerView.setPlayer(mSimpleExpoPlayer);
            mSimpleExpoPlayer.addListener(this);
            String userAgent = Util.getUserAgent(getActivity(), "bakingking");
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUri),
                    new DefaultDataSourceFactory(getActivity(), userAgent),
                    new DefaultExtractorsFactory(), null, null);
            mSimpleExpoPlayer.prepare(mediaSource);
            mSimpleExpoPlayer.setPlayWhenReady(true);
        }
    }

    private void resetExpoPlayer() {
        Logger.d(TAG,"resetExpoPlayer");
        mSimpleExpoPlayer.stop();
        mSimpleExpoPlayer.release();
        mSimpleExpoPlayer = null;
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object o) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {

    }

    @Override
    public void onLoadingChanged(boolean b) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        Logger.d(TAG,"onPlayerStateChanged");
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mSimpleExpoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mSimpleExpoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException e) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }
}

