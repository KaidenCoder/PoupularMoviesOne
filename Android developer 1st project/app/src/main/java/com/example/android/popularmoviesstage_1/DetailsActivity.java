package com.example.android.popularmoviesstage_1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity{

    /**
     * Defining constants.
     */
    private final static String TEXT_TITLE = "Title: ";
    private final static String TEXT_VOTE_AVERAGE = "Vote Average: ";
    private final static String TEXT_RELEASE_DATE = "Release Date: ";
    private final static String TEXT_OVERVIEW = "Overview: ";
    private static final String INTENT_MOVIE_KEY = "movieObject";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent parentIntent = getIntent();
        if (parentIntent != null){
            if(parentIntent.hasExtra(INTENT_MOVIE_KEY)){

                /*
                 * setHomeButtonEnabled() makes the icon and title in the action bar clickable
                 * setHomeButtonEnabled() calls onOptionsItemSelected() on the Activity
                 * where we can check the item.getItemId()
                 */
                getSupportActionBar().setHomeButtonEnabled(true);

                setContentView(R.layout.activity_detail);
                /*
                 * Activity used to display Movie details like
                 * title of the movie, movie vote average, movie release date, movie overview.
                 */
                ImageView moviePosterImageView = findViewById(R.id.im_movie_detail_poster);
                final ProgressBar moviePosterProgressBar = findViewById(R.id.pb_movie_detail_poster);
                TextView movieTitleTextView = findViewById(R.id.tv_movie_detail_title);
                TextView movieVoteAverageTextView = findViewById(R.id.tv_movie_detail_vote_average);
                TextView movieReleaseDateTextView = findViewById(R.id.tv_movie_detail_release_date);
                TextView movieOverviewTextView = findViewById(R.id.tv_movie_detail_overview);
                final TextView moviePosterErrorTextView = findViewById(R.id.tv_movie_detail_poster_error);

                Movie movie = getIntent().getExtras().getParcelable(INTENT_MOVIE_KEY);

                Context context = getApplicationContext();
                Picasso.with(context)
                        .load(movie.buildPosterPath(context))
                        .into(moviePosterImageView, new Callback(){

                            /**
                             * on Success is an abstract void function
                             * Called when the Task completes successfully.
                             */
                            @Override
                            public void onSuccess(){
                                moviePosterProgressBar.setVisibility(View.GONE);
                            }

                            /**
                             * on Error function Called to indicate an error.
                             */
                            @Override
                            public void onError(){
                                moviePosterProgressBar.setVisibility(View.GONE);
                                moviePosterErrorTextView.setRotation(-20);
                                moviePosterErrorTextView.setVisibility(View.VISIBLE);
                            }
                        });
                /*
                 * makeBold function makes the String text bold.
                 */
                movieTitleTextView.append(makeBold(TEXT_TITLE));
                movieTitleTextView.append(movie.getOriginalTitle());
                movieVoteAverageTextView.append(makeBold(TEXT_VOTE_AVERAGE));
                movieVoteAverageTextView.append(movie.getVoteAverage());
                movieReleaseDateTextView.append(makeBold(TEXT_RELEASE_DATE));
                movieReleaseDateTextView.append(movie.getReleaseDate());
                movieOverviewTextView.append(makeBold(TEXT_OVERVIEW));
                movieOverviewTextView.append(movie.getOverview());

                setTitle(movie.getOriginalTitle());
            }
        }
    }
    /**
     * @param string text to be styles in bold
     * @return the string bold text
     */
    private SpannableString makeBold(String string){
        SpannableString boldText = new SpannableString(string);
        boldText.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, boldText.length(), 0);
        return boldText;
    }

    /**
     *When the user selects an item from the options menu (including action items in the app bar),
     * the system calls our activity's onOptionsItemSelected() method.
     * This method passes the MenuItem selected. We can identify the item by calling getItemId(),
     * which returns the unique ID for the menu item (defined by the android:id attribute in the menu
     * resource or with an integer given to the add() method). We can match this ID against known menu
     * items to perform the appropriate action.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
