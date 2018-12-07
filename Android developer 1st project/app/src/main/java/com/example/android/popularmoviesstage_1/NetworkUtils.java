package com.example.android.popularmoviesstage_1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public final class NetworkUtils {

    //movie url data
    private static final String MOVIES_API_KEY = "";
    private static final String API_KEY_QUERY = "api_key";
    private static final String LANGUAGE_QUERY = "language";
    private static final String PAGE_QUERY = "page";
    private static final String MOVIES_API_URL = "https://api.themoviedb.org/3";
    private static final String MOVIES_POPULAR = "/movie/popular";
    private static final String MOVIES_TOP_RATED = "/movie/top_rated";

    public static boolean isOnline(Context context){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    //Building the movie url
    public static URL buildUrl(String method, Map<String, String> params){
        Uri.Builder builder = Uri.parse(MOVIES_API_URL + method).buildUpon();
        builder.appendQueryParameter(API_KEY_QUERY,MOVIES_API_KEY);
        for (Map.Entry<String, String> param : params.entrySet()){
            builder.appendQueryParameter(param.getKey(), param.getValue());
        }

        Uri uri = builder.build();
        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput){
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String getLanguageQuery(){
        return LANGUAGE_QUERY;
    }

    public static String getPageQuery(){
        return PAGE_QUERY;
    }

    public static String getMoviesPopular(){
        return MOVIES_POPULAR;
    }

    public static String getMoviesTopRated() {
        return MOVIES_TOP_RATED;
    }
}
