package com.example.android.popularmoviesstage_1;

import android.os.AsyncTask;
import android.view.View;

import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FetchMovieTask extends AsyncTask<String[], Void, List<Movie>>{

    public static final String MOVIEDB_LANGUAGE = "en-US";
    /*
     * onPreExecute(), invoked on the UI thread before the task is executed.
     * This step is normally used to setup the task, for instance by showing
     * a progress bar in the user interface.
     */
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        MoviesListFragment.mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    /*
     *doInBackground(Params...), invoked on the background thread immediately
     * after onPreExecute() finishes executing. This step is used to perform
     * background computation that can take a long time. The parameters of the
     * asynchronous task are passed to this step.
     */
    @Override
    protected List<Movie> doInBackground(String[]... params){
        String method = params[0][0];
        String page = params[0][1];
        Map<String, String> mapping = new HashMap<>();

        mapping.put(NetworkUtils.getLanguageQuery(), MOVIEDB_LANGUAGE);
        mapping.put(NetworkUtils.getPageQuery(), String.valueOf(page));

        URL url = NetworkUtils.buildUrl(method, mapping);

        try {
            String response = NetworkUtils.getResponseFromHttpUrl(url);
            JSONObject responseJson = new JSONObject(response);

            return MovieDbJson.getPopularMoviesListFromJson(responseJson);
            }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /*
     * onPostExecute(Result), invoked on the UI thread after the background
     * computation finishes. The result of the background computation is passed
     * to this step as a parameter.
     */
    @Override
    protected void onPostExecute(List<Movie> movieList){
        MoviesListFragment.mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (movieList != null) {
            MoviesListFragment.mMoviesAdapter.setMoviesData(movieList);
            MoviesListFragment.mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        } else {
            MoviesListFragment.mErrorMessageDisplay.setVisibility(View.VISIBLE);
        }
        MoviesListFragment.mSwipeContainer.setRefreshing(false);
    }
}
