package dragons.android.popularmovies;

import android.app.ActivityOptions;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;
import com.facebook.stetho.Stetho;
import java.util.ArrayList;
import java.util.List;
import dragons.android.popularmovies.data.AppDatabase;
import dragons.android.popularmovies.utilities.AppExecutors;
import dragons.android.popularmovies.utilities.HttpAsyncDataTask;
import dragons.android.popularmovies.models.Movie;
import dragons.android.popularmovies.adapters.MovieAdapter;


public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieClickHandler,HttpAsyncDataTask.OnTaskCompleted {

    // The first data that will show upon opening the app.
    private final static String INITIAL_ENDPOINT = "now_playing";

    // List of Movie objects that is necessary for the RecyclerView to be used
    private List<Movie> movies;
    private LiveData<List<Movie>> favorites;

    private static final String BUNDLE_LAYOUT = "layout";

    // Favorite booleans
    private static boolean doesExists;
    private static boolean mLiveDataSwitch;

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


    //Database variable
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);


        mDb = AppDatabase.getsInstance(getApplicationContext());
        mLiveDataSwitch = false;

        // Facebook Debugging tool that uses Chrome DevTools to do Network and
        // Database Inspections.
        // http://facebook.github.io/stetho/

        Stetho.initializeWithDefaults(this);

        movies = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MovieAdapter(movies, this);
        recyclerView.setAdapter(adapter);

        adapter.setOnMovieClickHandler(this);
        adapter.setMainOnFavoriteClickHandler(this);

        if(saveInstanceState == null) {



            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(NOW_PLAYING);
            }
            // Setup the recycler view with the GirdLayoutManger that spans two columns.



            if (checkNetwork()) {

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


                if(checkNetwork()) {

                    if(getSupportActionBar() != null){
                        getSupportActionBar().setTitle(MOST_POPULAR);
                    }
                    mLiveDataSwitch = false;
                    HttpAsyncDataTask task = new HttpAsyncDataTask(MainActivity.this);
                    task.execute(POPULAR);
                    return true;
                } else {
                    toastNoInternet(this);
                }
                break;

            case R.id.highestRated:
                if(checkNetwork()) {

                    if(getSupportActionBar() != null){
                        getSupportActionBar().setTitle(TOP_RATED_TITLE);
                    }
                    mLiveDataSwitch = false;
                    HttpAsyncDataTask task = new HttpAsyncDataTask(MainActivity.this);
                    task.execute(TOP_RATED);
                    return true;
                } else {
                    toastNoInternet(this);
                }
                break;

            case R.id.nowPlaying:
                if(checkNetwork()) {
                    if(getSupportActionBar() != null){
                        getSupportActionBar().setTitle(NOW_PLAYING);
                    }
                    mLiveDataSwitch = false;
                    HttpAsyncDataTask task = new HttpAsyncDataTask(MainActivity.this);
                    task.execute(INITIAL_ENDPOINT);
                    return true;
                }
                else {
                    toastNoInternet(this);
                }
                break;
            case R.id.favorites:
                if(checkNetwork()) {
                    if(getSupportActionBar() != null){
                        getSupportActionBar().setTitle(R.string.Favorites);

                    }
                    mLiveDataSwitch = true;
                    favorites = mDb.movieDAO().getAllFavoriteMovies();
                    favorites.observe(this, new Observer<List<Movie>>() {
                        @Override
                        public void onChanged(@Nullable List<Movie> favorites) {

                            // Prevents the movie data from switching to favorites if it's not in view
                            if(mLiveDataSwitch) {
                                movies = favorites;

                                LayoutAnimationController animationController = AnimationUtils
                                        .loadLayoutAnimation(MainActivity.this, R.anim.grid_translate_up);
                                recyclerView.setLayoutAnimation(animationController);
                                adapter.updateAdapter(movies, MainActivity.this);
                                if(movies.size() < 1){
                                    Toast.makeText(getApplicationContext(),"You have no favorites. Go add some!", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    });
                }
                else{
                    toastNoInternet(this);
                }

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieClick(int position) {


        if(checkNetwork()) {
            Intent detailViewIntent = new Intent(this, MovieDetailActivity.class);
            Movie movieSelected = movies.get(position);

            // Passing data to detail view with parcelable implementation
            detailViewIntent.putExtra(MOVIE_SELECTED, movieSelected);
            if(detailViewIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(detailViewIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            }
        }
        else {
            toastNoInternet(this);
        }
    }

    @Override
    public void onMainFavoriteClick(int position) {
        final Movie movieSelected = movies.get(position);
        final int movieId = movieSelected.getId();
        doesExists = false;

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                doesExists = mDb.movieDAO().doesExist(movieId);

                if(doesExists){
                    mDb.movieDAO().deleteMovie(movieId);
                    Log.d("Deleted Favorites","Item been deleted.");

                }
                else{
                    mDb.movieDAO().insertMovie(movieSelected);
                    Log.d("Insert Favorites", "Item been added");
                }
            }
        });

        adapter.notifyItemChanged(position);
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
    private boolean checkNetwork() {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        assert connMgr != null;
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        Log.d("Connection type: ", "Wifi connected: " + isWifiConn);
        Log.d("Connection type: ", "Mobile connected: " + isMobileConn);

        return isMobileConn || isWifiConn;
    }

    private void toastNoInternet(Context context) {
        Toast.makeText(context,"Unable to download list. No internet connection. Wait" +
                        " for internet to connect before trying again",
                Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        String title = "";

        recyclerView.getLayoutManager().onSaveInstanceState();

        int position = gridLayoutManager.findFirstVisibleItemPosition();
        if(getSupportActionBar() != null) {
            if(getSupportActionBar().getTitle() != null) {
                title = getSupportActionBar().getTitle().toString();
            }
        }


        outState.putString("actionBarTitle",title);
        outState.putParcelable(BUNDLE_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
        outState.putInt(LAST_POSITION,position);
        ArrayList<Movie> savedMovie = new ArrayList<>();
        savedMovie.addAll(movies);
        outState.putParcelableArrayList(MOVIES_SAVED,savedMovie);
        Log.d("onSaveInstanceState","items saved!");
    }


    @Override
    public void onRestoreInstanceState(Bundle inState){
        super.onRestoreInstanceState(inState);


        int position = inState.getInt(LAST_POSITION);
        String title = inState.getString("actionBarTitle");

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
        ArrayList<Movie> savedMovie;
        savedMovie = inState.getParcelableArrayList(MOVIES_SAVED);
        LayoutAnimationController animationController = AnimationUtils
                .loadLayoutAnimation(MainActivity.this,R.anim.grid_translate_up);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutAnimation(animationController);
        adapter.updateAdapter(savedMovie, MainActivity.this);
        recyclerView.getLayoutManager().onRestoreInstanceState(inState);
        gridLayoutManager.scrollToPosition(position);
        Log.d("onRestoreInstanceState","Was called!");

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("Lifecycle","onDestroy called");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d("Lifecycle", "onPause called");
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d("Lifecycle", "onStart called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d("Lifecycle", "onStop called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("Lifecycle", "onResume called");

    }


}
