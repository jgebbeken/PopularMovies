package dragons.android.popularmovies.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import dragons.android.popularmovies.Contracts.MovieFavoritesContract;
import dragons.android.popularmovies.Models.Movie;

/**
 * The FavoriteHelper is used to move some of the code off of the activities.
 * It also a centralized place to toggle button images to on or off depending
 * if a movie exist and query everything in the movieFavorites table to display
 * it back the the MainActivity RecyclerView.
 *
 */

public class FavoriteHelper {



    public static void addFavorites(Movie movie, Context context){




        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieFavoritesContract.MovieFavoritesEntry.MOVIE_TITLE, movie.getTitle());
        contentValues.put(MovieFavoritesContract.MovieFavoritesEntry.RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(MovieFavoritesContract.MovieFavoritesEntry.BACKDROP_URL, movie.getBackDropUrl());
        contentValues.put(MovieFavoritesContract.MovieFavoritesEntry.MOVIE_ID, movie.getId());
        contentValues.put(MovieFavoritesContract.MovieFavoritesEntry.OVERVIEW, movie.getOverview());
        contentValues.put(MovieFavoritesContract.MovieFavoritesEntry.POSTER_URL, movie.getPosterUrl());
        contentValues.put(MovieFavoritesContract.MovieFavoritesEntry.SMALL_IMAGE, movie.getSmallPosterUrl());
        contentValues.put(MovieFavoritesContract.MovieFavoritesEntry.VOTE_AVERAGE, movie.getVoteAverage());
        contentValues.put(MovieFavoritesContract.MovieFavoritesEntry.VOTE_COUNT, movie.getVoteCount());
        Log.d("Database", "Added values to database");

        context.getContentResolver().insert(MovieFavoritesContract.MovieFavoritesEntry.CONTENT_URI,
                contentValues);

    }

    public static boolean recordExists(String movieID, Context context) {

        boolean status;

        String[] column = { MovieFavoritesContract.MovieFavoritesEntry.MOVIE_ID };
        String selection = MovieFavoritesContract.MovieFavoritesEntry.MOVIE_ID +" =?";
        String[] selectionArgs = { movieID };


        Cursor cursor = context.getContentResolver().query(MovieFavoritesContract.MovieFavoritesEntry
        .CONTENT_URI.buildUpon().appendPath(movieID).build(),column,selection,selectionArgs,null);
        assert cursor != null;
        status = cursor.getCount() > 0;
        cursor.close();
        return status;
    }

    public static List<Movie> getFavorites(Context context){

        List<Movie> movies = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(MovieFavoritesContract.MovieFavoritesEntry
        .CONTENT_URI,null,null,null,null);


        assert cursor != null;
        if(cursor.moveToFirst()){
            for (cursor.moveToFirst();!cursor.isAfterLast(); cursor.moveToNext()){
                Movie movie = new Movie();
                movie.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                movie.setPosterFromCursor(cursor.getString(cursor.getColumnIndex("posterUrl")));
                movie.setSmallPosterFromCursor(cursor.getString(cursor.getColumnIndex("smallImage")));
                movie.setBackDropFromCursor(cursor.getString(cursor.getColumnIndex("backdropUrl")));
                movie.setId(cursor.getInt(cursor.getColumnIndex("movieId")));
                movie.setVoteCount(cursor.getInt(cursor.getColumnIndex("voteCount")));
                movie.setVoteAverage(cursor.getLong(cursor.getColumnIndex("voteAverage")));
                movie.setOverview(cursor.getString(cursor.getColumnIndex("overview")));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex("releaseDate")));

                // Store movie data from cursor to ArrayList.
                movies.add(movie);
            }
        }
        else {
            Toast.makeText(context,"You have no favorites",Toast.LENGTH_LONG).show();
            movies = new ArrayList<>(); // To prevent null pointer exceptions
            return movies;
        }
        cursor.close();
        return movies;
    }



}
