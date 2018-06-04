package dragons.android.popularmovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Movie object model used within this application. The Parcelable implementation is used to
 * quickly transfer Movie objects to Activities with ease with only one Intent.putExtra.
 */

public class Movie implements Parcelable {

    private String posterUrl;
    private String smallPosterUrl;
    private String backDropUrl;
    private String overview;
    private String releaseDate;
    private String title;
    private int voteCount;
    private double voteAverage;
    private int id;

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    private static final String IMAGE_FULL_SIZE_URL = "https://image.tmdb.org/t/p/original";
    private static final String IMAGE_SMALL_POSTER_URL = "https://image.tmdb.org/t/p/w300";



    public Movie(){

    }

    @SuppressWarnings("unused")
    public Movie(String posterUrl, String backDropUrl, String overview, String releaseDate, String title, int voteCount, double voteAverage, int id){

        this.posterUrl = IMAGE_BASE_URL + posterUrl;
        this.backDropUrl = IMAGE_BASE_URL + backDropUrl;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.title = title;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = IMAGE_BASE_URL + posterUrl;
        setSmallPosterUrl(posterUrl);
    }

    public void setPosterFromCursor(String posterUrl){
        this.posterUrl = posterUrl;
    }

    public String getBackDropUrl() {
        return backDropUrl;
    }

    public void setBackDropUrl(String backDropUrl) {
        this.backDropUrl = IMAGE_FULL_SIZE_URL + backDropUrl;
    }

    public void setBackDropFromCursor(String backDropUrl){
        this.backDropUrl = backDropUrl;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    private void setSmallPosterUrl(String posterUrl){
        smallPosterUrl = IMAGE_SMALL_POSTER_URL + posterUrl;
    }

    public void setSmallPosterFromCursor(String smallPosterUrl){
        this.smallPosterUrl = smallPosterUrl;
    }

    public String getSmallPosterUrl() {
        return smallPosterUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.posterUrl);
        dest.writeString(this.smallPosterUrl);
        dest.writeString(this.backDropUrl);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.title);
        dest.writeInt(this.voteCount);
        dest.writeDouble(this.voteAverage);
        dest.writeInt(this.id);
    }

    private Movie(Parcel in) {
        this.posterUrl = in.readString();
        this.smallPosterUrl = in.readString();
        this.backDropUrl = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.title = in.readString();
        this.voteCount = in.readInt();
        this.voteAverage = in.readDouble();
        this.id = in.readInt();
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
