package com.example.android.popularmoviesstage_1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterAdapterViewHolder>{

    private List<Movie> mMovieList;

    private static final String INTENT_MOVIE_KEY = "movieObject";

    //A ViewHolder describes an item view and metadata about its place within the RecyclerView.
    public class PosterAdapterViewHolder extends RecyclerView.ViewHolder{
        final CardView mPopularMovieCardView;
        final ImageView mPosterImageView;
        public final ProgressBar mPosterProgressBar;
        public final TextView mErrorTextView;
        final TextView mTitleTextView;
        Context mContext;

        PosterAdapterViewHolder(View view){
            super(view);
            mPopularMovieCardView = view.findViewById(R.id.cv_popular_movie);
            mPosterImageView = view.findViewById(R.id.iv_movie_poster);//from movies_card.xml
            mPosterProgressBar = view.findViewById(R.id.pb_movie_poster);//from movies_card.xml
            mErrorTextView = view.findViewById(R.id.tv_movie_poster_error);//from movies_card.xml
            mTitleTextView = view.findViewById(R.id.tv_movie_title);//from movies_card.xml
            mContext = view.getContext();
        }
    }

    //onCreateViewHolder only creates a new view holder when there are no existing view holders which
    // the RecyclerView can reuse. So, for instance, if my RecyclerView can display 5 items at a time,
    // it will create 5-6 ViewHolders, and then automatically reuse them, each time calling onBindViewHolder.
    @Override
    public PosterAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        Context context = viewGroup.getContext();
        int layoutListItem = R.layout.movies_card;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutListItem, viewGroup, shouldAttachToParentImmediately);
        return new PosterAdapterViewHolder(view);
    }

    //onBindViewHolder() binds the item to the RecyclerView holder.
    @Override
    public void onBindViewHolder(PosterAdapterViewHolder posterAdapterViewHolder,int position){
        //getting the position of the movie
        Movie movie = mMovieList.get(position);

        //loading the poster image of the movie
        Picasso.with(posterAdapterViewHolder.mContext)
                .load(movie.buildPosterPath(posterAdapterViewHolder.mContext))
                .into(posterAdapterViewHolder.mPosterImageView, new MoviePoster(posterAdapterViewHolder));
        posterAdapterViewHolder.mTitleTextView.setText(movie.getOriginalTitle());

        posterAdapterViewHolder.mPopularMovieCardView.setTag(R.id.card_view_item, position);

        posterAdapterViewHolder.mPopularMovieCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Class destination = DetailsActivity.class;
                Intent detailIntent = new Intent(view.getContext(), destination);
                int position = (int) view.getTag(R.id.card_view_item);
                detailIntent.putExtra(INTENT_MOVIE_KEY, mMovieList.get(position));
                view.getContext().startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount(){
        if (null == mMovieList)return 0;
        return mMovieList.size();
    }

    public void clear(){
        if(mMovieList != null){
            mMovieList.clear();
            notifyDataSetChanged();
        }
    }

    public void setMoviesData(List<Movie> movieList){
        if (null == mMovieList){
            mMovieList = movieList;
        } else {
            mMovieList.addAll(movieList);
        }
        notifyDataSetChanged();
    }

    public List<Movie> getMoviesData(){
        return mMovieList;
    }
}
