package dragons.android.popularmovies.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import dragons.android.popularmovies.models.Movie;
import dragons.android.popularmovies.utilities.MovieDAO;

@Database(entities = {Movie.class}, version = 2, exportSchema = false)
/*
 *  The AppDatabase for the room implementation
 */

public abstract class AppDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME ="movieFavorites.db";
    private static AppDatabase sInstance;


    // If the database schema changed or a new version is brought out it is set the destroy table.
    // For development purpose only.

    public static AppDatabase getsInstance(Context context){
        if(sInstance == null) {
             synchronized (LOCK) {
                 sInstance = Room.databaseBuilder(context.getApplicationContext(),
                         AppDatabase.class,AppDatabase.DATABASE_NAME)
                         .fallbackToDestructiveMigration()
                         .build();
             }
        }
        return sInstance;
    }

    public abstract MovieDAO movieDAO();
}
