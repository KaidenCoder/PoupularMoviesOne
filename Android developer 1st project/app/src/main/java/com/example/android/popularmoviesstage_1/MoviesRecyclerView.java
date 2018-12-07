package com.example.android.popularmoviesstage_1;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

//OnScrollListener is added to the RecyclerView to receive messages when a scrolling event has occurred on that RecyclerView.
public abstract class MoviesRecyclerView extends RecyclerView.OnScrollListener {
    private int mVisibleThreshold;
    private int mCurrentPage;
    private int mPreviousTotalItemCount = 0;
    private boolean mLoading = true;
    private final int mStartingPageIndex = 1;

    private final RecyclerView.LayoutManager mLayoutManager;

    protected MoviesRecyclerView(GridLayoutManager layoutManager, int page){
        this.mLayoutManager = layoutManager;
        mVisibleThreshold = mVisibleThreshold * layoutManager.getSpanCount();
        mCurrentPage = page;
    }

    //Checking scrolling on the items when items is greater than zero.
    @Override
    public void onScrolled(RecyclerView view, int horizontalItems, int verticalItems){
        if(verticalItems > 0){
            int itemCount = mLayoutManager.getItemCount();
            int lastPositionItemViewed = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();

            if (itemCount < mPreviousTotalItemCount){
                this.mCurrentPage = this.mStartingPageIndex;
                this.mPreviousTotalItemCount = itemCount;
                if (itemCount == 0){
                    this.mLoading = true;
                }
            }

            if (mLoading && (itemCount > mPreviousTotalItemCount)){
                mLoading = false;
                mPreviousTotalItemCount = itemCount;
            }

            if(!mLoading && (lastPositionItemViewed + mVisibleThreshold) > itemCount){
                mCurrentPage++;
                onLoadMore(mCurrentPage, itemCount, view);
                mLoading = true;
            }
        }
    }

    //Start scrolling from the beginning
    public void resetState(){
        this.mCurrentPage = this.mStartingPageIndex;
        this.mPreviousTotalItemCount = 0;
        this.mLoading = true;
    }

    public abstract void onLoadMore(int page, int itemCount, RecyclerView view);
}
