package dragons.android.popularmovies.ContentProviders;


import android.content.ContentProvider;
import android.content.ContentValues;

import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import dragons.android.popularmovies.Contracts.MovieFavoritesContract;
import dragons.android.popularmovies.Helpers.FavoritesDbHelper;

public class FavoritesContentProvider  extends ContentProvider {


    private FavoritesDbHelper mDbhelper;

    private static final int ALL_FAVORITES = 100;
    private static final int MOVIES_WITH_MOVIE_ID = 101;


    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieFavoritesContract.CONTENT_AUTHORITY;


        // Entire table
        matcher.addURI(authority,MovieFavoritesContract.MovieFavoritesEntry.TABLE_NAME, ALL_FAVORITES);

        // Movie with TMDB api id
        matcher.addURI(authority,MovieFavoritesContract.MovieFavoritesEntry.TABLE_NAME + "/#", MOVIES_WITH_MOVIE_ID);

        return matcher;

    }


    @Override
    public boolean onCreate() {

        mDbhelper = new FavoritesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = mDbhelper.getWritableDatabase();
        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {
            case ALL_FAVORITES:
                retCursor = db.query(MovieFavoritesContract.MovieFavoritesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            case MOVIES_WITH_MOVIE_ID:
                retCursor = db.query(
                         MovieFavoritesContract.MovieFavoritesEntry.TABLE_NAME,
                         projection,
                         MovieFavoritesContract.MovieFavoritesEntry.MOVIE_ID + " = ?",
                         selectionArgs,
                        null,
                         null,
                         null);

                 break;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);

        }

        if(getContext() != null) {
            retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase db = mDbhelper.getWritableDatabase();
        Uri returnUri;

        switch (sUriMatcher.match(uri)){
            case ALL_FAVORITES:

                long id = db.insert(MovieFavoritesContract.MovieFavoritesEntry.TABLE_NAME,
                        null,contentValues);

                if(id != -1) {
                    returnUri = MovieFavoritesContract.MovieFavoritesEntry.buildMovieWithId(id);
                    Log.d("Provider: ", "Data added to database." +returnUri.toString());
                }
                else {
                    throw new UnsupportedOperationException("Movie row not created into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(getContext() != null)
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }




    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = mDbhelper.getWritableDatabase();

        int rows;
        final int match = sUriMatcher.match(uri);


        switch (match){
            case MOVIES_WITH_MOVIE_ID:
                rows = db.delete(MovieFavoritesContract.MovieFavoritesEntry.TABLE_NAME,
                        selection + "= ?",selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(selection == null || rows != 0){
            if(getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
        }


        return rows;
    }


    // Movies data does not need to be updated since API for movies stays the same.
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
