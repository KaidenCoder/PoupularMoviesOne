package com.example.android.popularmoviesstage_1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieDbJson {

    private static final String STATUS_CODE = "status_code";
    private static final String STATUS_MESSAGE = "status_message";
    private static final int STATUS_INVALID_API_KEY = 7;
    private static final int STATUS_INVALID_RESOURCE = 34;
    private static final String SUCCESS = "success";

    private static final String TAG = MovieDbJson.class.getSimpleName();

    /*
     * getPopularMoviesListFromJson usage can be found in FetchMovieTask.java.
     */
    public static List<Movie> getPopularMoviesListFromJson(JSONObject popularMovies)
            throws JSONException {

        /* JSON parsing utilities
         * Here the parse details are defined in constants.
         */
        final String RESULTS = "results";
        final String POSTER_PATH = "poster_path";
        final String ID = "id";
        final String OVERVIEW = "overview";
        final String ORIGINAL_TITLE = "original_title";
        final String RELEASE_DATE = "release_date";
        final String VOTE_AVERAGE = "vote_average";

        List<Movie> parsedMoviesData;

        /*
         * Checking valid details of movie Api Key and  movie resources.
         */
        if (popularMovies.has(SUCCESS) && !popularMovies.getBoolean(SUCCESS)){
            int errorCode = popularMovies.getInt(STATUS_CODE);
            String message = popularMovies.getString(STATUS_MESSAGE);

            switch (errorCode){
                case STATUS_INVALID_API_KEY:
                    // Invalid API key provided
                    Log.d(TAG, message);
                    return null;
                case STATUS_INVALID_RESOURCE:
                    // Invalid resource
                    Log.d(TAG, message);
                    return null;
                default:
                    // Server probably down
                    return null;

            }
        }

        JSONArray resultsArray = popularMovies.getJSONArray(RESULTS);

        parsedMoviesData = new ArrayList<>();

        for(int count=0; count< resultsArray.length(); count++){

            //JSON parsing
            JSONObject result = resultsArray.getJSONObject(count);

            int id = result.getInt(ID);
            String posterPath = result.getString(POSTER_PATH);
            String overview = result.getString(OVERVIEW);
            String originalTitle = result.getString(ORIGINAL_TITLE);
            String popularity = result.getString(RELEASE_DATE);
            String voteAverage = result.getString(VOTE_AVERAGE);

            Movie movie = new Movie(id, posterPath, overview, originalTitle, popularity, voteAverage);
            parsedMoviesData.add(movie);
        }
        return parsedMoviesData;
    }
}
