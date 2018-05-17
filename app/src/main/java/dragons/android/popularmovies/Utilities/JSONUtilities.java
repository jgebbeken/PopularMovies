package dragons.android.popularmovies.Utilities;





import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * The purpose of this JSON Utilities is to provide a way to pull from the TMDB api and put
 * them in the movie object
 */

class JSONUtilities {

    private final static String ARRAY_RESULTS = "results";
    private final static String TITLE = "title";
    private final static String OVERVIEW = "overview";
    private final static String BACKDROP = "backdrop_path";
    private final static String POSTER = "poster_path";
    private final static String VOTE_AVERAGE = "vote_average";
    private final static String VOTE_COUNT = "vote_count";
    private final static String RELEASE_DATE = "release_date";
    private final static String ID = "id";

    private final static String VIDEO_OBJECT = "videos";
    private final static String VIDEO_NAME = "name";
    private final static String VIDEO_URL = "key";
    private final static String VIDEO_RES = "size";
    private final static String VIDEO_TYPE = "type";

    private final static String REVIEW_OBJECT = "reviews";
    private final static String AUTHOR = "author";
    private final static String CONTENT = "content";



    static List<Movie> movieParsing(String json){


        List<Movie> movies = new ArrayList<>();

        try {
            JSONObject jsonMovies = new JSONObject(json);
            JSONArray moviesArray = jsonMovies.getJSONArray(ARRAY_RESULTS);

            for(int i = 0; i < moviesArray.length(); i++){
                JSONObject obj = moviesArray.getJSONObject(i);

                Movie movie = new Movie();

                movie.setId(obj.optInt(ID));
                movie.setTitle(obj.optString(TITLE));
                movie.setOverview(obj.optString(OVERVIEW));
                movie.setReleaseDate(obj.optString(RELEASE_DATE));
                movie.setBackDropUrl(obj.optString(BACKDROP));
                movie.setPosterUrl(obj.optString(POSTER));
                movie.setVoteAverage(obj.optDouble(VOTE_AVERAGE));
                movie.setVoteCount(obj.optInt(VOTE_COUNT));

                movies.add(movie);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return movies;
    }

    static ReviewsAndVideos videoAndReviewParsing(String json) {


        List<Video> videos = new ArrayList<>();
        List<Review> reviews = new ArrayList<>();

        try {
            JSONObject jsonVideos = new JSONObject(json).getJSONObject(VIDEO_OBJECT);
            JSONArray videosArray = jsonVideos.getJSONArray(ARRAY_RESULTS);

            for(int i = 0; i < videosArray.length(); i++) {

                Video video = new Video();

                JSONObject object = videosArray.getJSONObject(i);

                video.setVideoName(object.optString(VIDEO_NAME));
                video.setVideoRes(object.optString(VIDEO_RES));
                video.setType(object.optString(VIDEO_TYPE));
                video.setVideoUrl(object.optString(VIDEO_URL));

                videos.add(video);
            }

            JSONObject jsonReviews = new JSONObject(json).getJSONObject(REVIEW_OBJECT);
            JSONArray reviewArray = jsonReviews.getJSONArray(ARRAY_RESULTS);

            for(int i = 0; i < jsonReviews.length(); i++){

                Review review = new Review();

                JSONObject object = reviewArray.getJSONObject(i);

                review.setAuthor(object.optString(AUTHOR));
                review.setContent(object.optString(CONTENT));

                reviews.add(review);
            }

            ReviewsAndVideos reviewsAndVideos;
            reviewsAndVideos = new ReviewsAndVideos(reviews,videos);
            return reviewsAndVideos;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
