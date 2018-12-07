package com.example.android.popularmoviesstage_1;

import android.view.View;

import com.squareup.picasso.Callback;

//Callback to get onSuccess and onError events.
public class MoviePoster extends Callback.EmptyCallback{

    private final PosterAdapter.PosterAdapterViewHolder mViewHolder;

    public MoviePoster(PosterAdapter.PosterAdapterViewHolder viewHolder ){
        this.mViewHolder = viewHolder;
    }

    /**
     * on Success is an abstract void function
     * Called when the Task completes successfully.
     */
    @Override
    public void onSuccess(){
        mViewHolder.mPosterProgressBar.setVisibility(View.GONE);
        mViewHolder.mErrorTextView.setVisibility(View.GONE);
    }

    /**
     * on Error function Called to indicate an error.
     */
    @Override
    public void onError(){
        mViewHolder.mPosterProgressBar.setVisibility(View.GONE);
        mViewHolder.mErrorTextView.setRotation(-20);
        mViewHolder.mErrorTextView.setVisibility(View.VISIBLE);
    }
}
