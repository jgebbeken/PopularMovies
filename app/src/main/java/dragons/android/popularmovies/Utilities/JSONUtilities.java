package dragons.android.popularmovies.Utilities;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jgebbeken
 * The purpose of this JSON Utilities is to provide a way to pull from the TMDB api and put
 * them in the movie object
 */

public class JSONUtilities {

    private final static String ARRAY_RESULTS = "results";
    private final static String TITLE = "title";
    private final static String OVERVIEW = "overview";
    private final static String BACKDROP = "backdrop_path";
    private final static String POSTER = "poster_path";
    private final static String VOTE_AVERAGE = "vote_average";
    private final static String VOTE_COUNT = "vote_count";
    private final static String RELEASE_DATE = "release_date";
    private final static String ID = "id";



    public static List<Movie> movieParsing(String json){


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
}
