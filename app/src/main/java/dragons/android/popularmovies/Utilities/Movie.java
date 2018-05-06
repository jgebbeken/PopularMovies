package dragons.android.popularmovies.Utilities;

/**
 * Created by jgebbeken on 4/29/2018.
 */

public class Movie {

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

    public Movie(String posterUrl, String backDropUrl, String overview, String releaseDate, String title, int voteCount, double voteAverage, int id){

        this.posterUrl = IMAGE_BASE_URL + posterUrl;
        this.backDropUrl = IMAGE_BASE_URL + backDropUrl;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.title = title;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
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

    public String getBackDropUrl() {
        return backDropUrl;
    }

    public void setBackDropUrl(String backDropUrl) {
        this.backDropUrl = IMAGE_FULL_SIZE_URL + backDropUrl;
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

    public void setSmallPosterUrl(String posterUrl){
        smallPosterUrl = IMAGE_SMALL_POSTER_URL + posterUrl;
    }

    public String getSmallPosterUrl() {
        return smallPosterUrl;
    }


}
