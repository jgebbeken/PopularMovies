package dragons.android.popularmovies.Utilities;

/**
 * Created by jgebbeken on 4/29/2018.
 */

public class Movie {

    private String posterUrl;
    private String backDropUrl;
    private String overview;
    private String releaseDate;
    private String title;
    private int voteCount;
    private int voteAverage;
    private int id;

    private final static String IMAGE_BASE_URL ="https://image.tmdb.org/t/p/w500/";


    public Movie(){

    }

    public Movie(String posterUrl, String backDropUrl, String overview, String releaseDate, String title, int voteCount, int voteAverage, int id){

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
    }

    public String getBackDropUrl() {
        return backDropUrl;
    }

    public void setBackDropUrl(String backDropUrl) {
        this.backDropUrl = IMAGE_BASE_URL + backDropUrl;
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

    public int getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(int voteAverage) {
        this.voteAverage = voteAverage;
    }





}
