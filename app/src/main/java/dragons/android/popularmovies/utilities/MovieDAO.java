package dragons.android.popularmovies.utilities;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import dragons.android.popularmovies.models.Movie;

/**
 * Created by jgebbeken on 6/5/2018.
 */

@Dao
public interface MovieDAO {

    @Query("SELECT * FROM movieFavorites WHERE movieId = :movieId")
    boolean doesExist(int movieId);

    @Query("SELECT * FROM movieFavorites")
    LiveData<List<Movie>> getAllFavoriteMovies();

    @Insert
    void insertMovie(Movie movie);

    @Query("DELETE FROM movieFavorites WHERE movieId = :movieId")
    void deleteMovie(int movieId);

}
