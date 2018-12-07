package com.example.android.popularmoviesstage_1;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;


public class Movie implements Parcelable {
    /**
     * Defining constants for the movie details.
     * Each of these details form a part of the user interface of a movie.
     */
    private final int id;
    private final String posterPath;
    private final String overview;
    private final String originalTitle;
    private final String releaseDate;
    private final String voteAverage;

    /**
     * Poster Image URL defined
     */
    private static final String POSTER_IMG_URL = "http://image.tmdb.org/t/p/";

    public Movie(int id, String posterPath, String overview, String originalTitle,
                 String releaseDate, String voteAverage){
        this.id = id;
        this.posterPath = posterPath;
        this.overview = overview;
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
    }

    /**
     * @param parcel
     * Container for a message (data and object references) that can be sent through an IBinder.
     */
    private Movie(Parcel parcel){
        id = parcel.readInt();
        posterPath = parcel.readString();
        overview = parcel.readString();
        originalTitle = parcel.readString();
        releaseDate = parcel.readString();
        voteAverage = parcel.readString();
    }

    public String buildPosterPath(Context context){
        String posterWidth = context.getResources().getString(R.string.poster_size);
        return  POSTER_IMG_URL + posterWidth + getPosterPath();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags){
        parcel.writeInt(id);
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(originalTitle);
        parcel.writeString(releaseDate);
        parcel.writeString(voteAverage);
    }

    /**
     * The Parcelable protocol provides an extremely efficient (but low-level) protocol
     * \for objects to write and read themselves from Parcels.
     */
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        @Override
        public Movie createFromParcel(Parcel parcel){
            return new Movie(parcel);
        }

        /**
         * This function is creating an array of movie details list like id, overview.
         */
        @Override
        public Movie[] newArray(int i){
            return  new Movie[i];
        }
    };

    public String getPosterPath(){
        return posterPath;
    }
    public String getOverview() {
        return overview;
    }
    public String getOriginalTitle() {
        return originalTitle;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public String getVoteAverage() {
        return voteAverage;
    }

}
