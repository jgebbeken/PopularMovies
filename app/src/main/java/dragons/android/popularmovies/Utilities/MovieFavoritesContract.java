package dragons.android.popularmovies.Utilities;

import android.provider.BaseColumns;

/**
 * Created by jgebbeken on 5/12/2018.
 */

public class MovieFavoritesContract {

    public static final class MovieFavoritesEntry implements BaseColumns {

       //These are the member for table name and the database columns
        public static final String TABLE_NAME = "movieFavorites";
        public static final String OVERVIEW = "overview";
        public static final String COLUMN_TITLE = "title";
        public static final String POSTER_URL = "posterUrl";
        public static final String SMALL_IMAGE = "smallImage";
        public static final String BACKDROP_URL = "backdropUrl";
        public static final String VOTE_COUNT = "voteCount";
        public static final String VOTE_AVERAGE = "voteAverage";
        public static final String VOTE_RATING = "voteRating";
        public static final String MOVIE_ID = "movieId";


    }
}
