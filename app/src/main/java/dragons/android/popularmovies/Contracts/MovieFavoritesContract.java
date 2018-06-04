package dragons.android.popularmovies.Contracts;

import android.net.Uri;
import android.provider.BaseColumns;


/**
 *  The Contract and where the part of the content provider is stored.
 */

public class MovieFavoritesContract {



    public static final String CONTENT_AUTHORITY = "dragons.android.popularmovies";

    private static final Uri BASE_CONTENT_URL = Uri.parse("content://" + CONTENT_AUTHORITY);



    public static final class MovieFavoritesEntry implements BaseColumns {


       //These are the member for table name and the database columns
        public static final String TABLE_NAME = "movieFavorites";
        public static final String OVERVIEW = "overview";
        public static final String MOVIE_TITLE = "title";
        public static final String POSTER_URL = "posterUrl";
        public static final String SMALL_IMAGE = "smallImage";
        public static final String BACKDROP_URL = "backdropUrl";
        public static final String VOTE_COUNT = "voteCount";
        public static final String VOTE_AVERAGE = "voteAverage";
        public static final String MOVIE_ID = "movieId";
        public static final String RELEASE_DATE = "releaseDate";


        // Used to query the entire movieFavorites table.
        public static final Uri CONTENT_URI = BASE_CONTENT_URL.buildUpon()
                .appendPath(TABLE_NAME)
                .build();


        /**
         * This content URI path looks up a specified movie based on its unique primary key
         * (_ID) and can manipulate it
         * @param id the unique Id for the movie.
         * @return the returns the row in questioned.
         */

        public static Uri buildMovieWithId(long id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }

    }
}
