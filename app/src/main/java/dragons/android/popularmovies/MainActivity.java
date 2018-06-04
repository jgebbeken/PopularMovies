package dragons.android.popularmovies;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.Toast;
import com.facebook.stetho.Stetho;
import java.util.ArrayList;
import java.util.List;
import dragons.android.popularmovies.Contracts.MovieFavoritesContract;
import dragons.android.popularmovies.Helpers.FavoriteHelper;
import dragons.android.popularmovies.Utilities.HttpAsyncDataTask;
import dragons.android.popularmovies.Models.Movie;
import dragons.android.popularmovies.Adapters.MovieAdapter;


public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieClickHandler,HttpAsyncDataTask.OnTaskCompleted {

    // The first data that will show upon opening the app.
    private final static String INITIAL_ENDPOINT = "now_playing";

    // List of Movie objects that is necessary for the RecyclerView to be used
    private List<Movie> movies;

    private static final String BUNDLE_LAYOUT = "layout";

    // RecyclerView Items
    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    //onSaveInstanceState items
    private static final String MOVIES_SAVED = "movieSaved";
    private static final String LAST_POSITION = "lastPosition";

    // Intent Items
    private static final String MOVIE_SELECTED = "movieSelected";

    private static final int SPAN_COUNT = 2;
    private GridLayoutManager gridLayoutManager;


    // Additional endpoints for users to select
    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";

    //SupportActionBar titles
    private static final String NOW_PLAYING ="Now Playing";
    private static final String TOP_RATED_TITLE = "Top Rated";
    private static final String MOST_POPULAR = "Most Popular";


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);


        // Facebook Debugging tool that uses Chrome DevTools to do Network and
        // Database Inspections.
        // http://facebook.github.io/stetho/

        Stetho.initializeWithDefaults(this);

        movies = new ArrayList<>();


        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(NOW_PLAYING);
        }
        // Setup the recycler view with the GirdLayoutManger that spans two columns.
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(this,SPAN_COUNT);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MovieAdapter(movies, this);
        recyclerView.setAdapter(adapter);

        adapter.setOnMovieClickHandler(this);
        adapter.setMainOnFavoriteClickHandler(this);

        if(saveInstanceState != null){
            ArrayList<Movie> savedMovie;
            savedMovie = saveInstanceState.getParcelableArrayList(MOVIES_SAVED);
            LayoutAnimationController animationController = AnimationUtils
                    .loadLayoutAnimation(MainActivity.this,R.anim.grid_translate_up);
            recyclerView.setLayoutAnimation(animationController);
            adapter.updateAdapter(savedMovie, MainActivity.this);
            recyclerView.getLayoutManager().onRestoreInstanceState(saveInstanceState);
            gridLayoutManager.scrollToPosition(saveInstanceState.getInt(LAST_POSITION));

        }
        else{
            if (checkNetwork(this)) {

                // We need to do the networking outside the main thread. JSON is downloaded outside the
                // main thread as well.
                HttpAsyncDataTask task = new HttpAsyncDataTask(MainActivity.this);
                task.execute(INITIAL_ENDPOINT);

            } else {
                toastNoInternet(this);
            }
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mostPopular:


                if(checkNetwork(this)) {

                    if(getSupportActionBar() != null){
                        getSupportActionBar().setTitle(MOST_POPULAR);
                    }
                    HttpAsyncDataTask task = new HttpAsyncDataTask(MainActivity.this);
                    task.execute(POPULAR);
                    return true;
                } else {
                    toastNoInternet(this);
                }
                break;

            case R.id.highestRated:
                if(checkNetwork(this)) {

                    if(getSupportActionBar() != null){
                        getSupportActionBar().setTitle(TOP_RATED_TITLE);
                    }
                    HttpAsyncDataTask task = new HttpAsyncDataTask(MainActivity.this);
                    task.execute(TOP_RATED);
                    return true;
                } else {
                    toastNoInternet(this);
                }
                break;

            case R.id.nowPlaying:
                if(checkNetwork(this)) {
                    if(getSupportActionBar() != null){
                        getSupportActionBar().setTitle(NOW_PLAYING);
                    }
                    HttpAsyncDataTask task = new HttpAsyncDataTask(MainActivity.this);
                    task.execute(INITIAL_ENDPOINT);
                    return true;
                }
                else {
                    toastNoInternet(this);
                }
                break;
            case R.id.favorites:
                if(checkNetwork(this)) {
                    if(getSupportActionBar() != null){
                        getSupportActionBar().setTitle(R.string.Favorites);
                    }
                    List<?> favorites;
                    favorites = FavoriteHelper.getFavorites(this);

                    onTaskCompleted(favorites);

                }

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieClick(int position) {
        Intent detailViewIntent = new Intent(this,MovieDetailActivity.class);
        Movie movieSelected = movies.get(position);

        // Passing data to detail view with parcelable implementation
        detailViewIntent.putExtra(MOVIE_SELECTED,movieSelected);
        startActivity(detailViewIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public void onMainFavoriteClick(int position) {
        Movie movieSelected = movies.get(position);
        String movieId = String.valueOf(movieSelected.getId());

        ImageView imageView = findViewById(R.id.favoriteStar);

        if(FavoriteHelper.recordExists(movieId,this)){
            getContentResolver().delete(MovieFavoritesContract.MovieFavoritesEntry.CONTENT_URI.buildUpon().appendPath(movieId)
                            .build(),
                    MovieFavoritesContract.MovieFavoritesEntry.MOVIE_ID, new String[]{String.valueOf(movieSelected.getId())});
            imageView.setImageResource(R.drawable.btn_star_big_off);
        }
        else{
            FavoriteHelper.addFavorites(movieSelected,this);
            imageView.setImageResource(R.drawable.btn_star_big_on);
        }

    }


    // Uses the HttpAsyncDataTask to handle background HTTP tasks and returns list of Movie objects
    // to give it to the adapter
    @Override
    public void onTaskCompleted(List<?> response) {

        movies = (List<Movie>) response;
        LayoutAnimationController animationController = AnimationUtils
                .loadLayoutAnimation(MainActivity.this,R.anim.grid_translate_up);
        recyclerView.setLayoutAnimation(animationController);
        adapter.updateAdapter(movies, MainActivity.this);

    }

    // Checks the network to see if there is no internet then return a boolean back based on
    // connectivity.
    private boolean checkNetwork(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null ) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } else{
            return false;
        }

    }

    private void toastNoInternet(Context context) {
        Toast.makeText(context,"Unable to download list. No internet connection. Wait" +
                        " for internet to connect before trying again",
                Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        recyclerView.getLayoutManager().onSaveInstanceState();

        int position = gridLayoutManager.findFirstCompletelyVisibleItemPosition();

        outState.putParcelable(BUNDLE_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
        outState.putInt(LAST_POSITION,position);
        ArrayList<Movie> savedMovie = new ArrayList<>();
        savedMovie.addAll(movies);
        outState.putParcelableArrayList(MOVIES_SAVED,savedMovie);
    }



}
