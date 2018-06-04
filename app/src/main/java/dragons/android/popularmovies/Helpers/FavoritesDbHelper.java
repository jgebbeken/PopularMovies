package dragons.android.popularmovies.Helpers;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import static dragons.android.popularmovies.Contracts.MovieFavoritesContract.*;

public class FavoritesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movieFavorites.db";

    private static final int DATABASE_VERSION = 3;


    public FavoritesDbHelper(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {



        final String SQL_CREATE_MOVIE_FAVORITES_TABLE = "CREATE TABLE " +
                MovieFavoritesEntry.TABLE_NAME + " (" +
                MovieFavoritesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieFavoritesEntry.OVERVIEW + " TEXT NOT NULL, " +
                MovieFavoritesEntry.MOVIE_TITLE + " TEXT NOT NULL, " +
                MovieFavoritesEntry.RELEASE_DATE + " TEXT, " +
                MovieFavoritesEntry.POSTER_URL + " TEXT NOT NULL, " +
                MovieFavoritesEntry.SMALL_IMAGE + " TEXT NOT NULL, " +
                MovieFavoritesEntry.BACKDROP_URL + " TEXT NOT NULL, " +
                MovieFavoritesEntry.VOTE_COUNT + " INTEGER NOT NULL," +
                MovieFavoritesEntry.VOTE_AVERAGE + " REAL, " +
                MovieFavoritesEntry.MOVIE_ID + " TEXT NOT NULL" +
                "); ";


        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_FAVORITES_TABLE);
        Log.d("Database", "Items should have been created in database");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {



        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieFavoritesEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);

    }



}
