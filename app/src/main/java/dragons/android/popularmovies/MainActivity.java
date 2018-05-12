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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dragons.android.popularmovies.Utilities.HttpAsyncDataTask;
import dragons.android.popularmovies.Utilities.Movie;
import dragons.android.popularmovies.Utilities.MovieAdapter;


public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieClickHandler,HttpAsyncDataTask.OnTaskCompleted {

    // The first data that will show upon opening the app.
    private final static String INITIAL_ENDPOINT = "now_playing";

    // List of Movie objects that is necessary for the RecyclerView to be used
    private List<Movie> movies;

    // RecyclerView Items
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    public static final int SPAN_COUNT = 2;

    // Additional endpoints for users to select
    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";

    //SupportActionBar titles
    public static final String NOW_PLAYING ="Now Playing";
    public static final String TOP_RATED_TITLE = "Top Rated";
    public static final String MOST_POPULAR = "Most Popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<Movie>();


            getSupportActionBar().setTitle(NOW_PLAYING);

            // Setup the recycler view with the GirdLayoutManger that spans two columns.
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
            adapter = new MovieAdapter(movies, this);


            recyclerView.setAdapter(adapter);

            adapter.setOnMovieClickHandler(this);

        if(checkNetwork(this)) {

            // We need to do the networking outside the main thread. JSON is downloaded outside the
            // main thread as well.
            //new ProcessMovieTask().execute(INITIAL_ENDPOINT);
            HttpAsyncDataTask task = new HttpAsyncDataTask(MainActivity.this);
            task.execute(INITIAL_ENDPOINT);


        }
        else {
            toastNoInternet(this);
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
                    getSupportActionBar().setTitle(MOST_POPULAR);
                    HttpAsyncDataTask task = new HttpAsyncDataTask(MainActivity.this);
                    task.execute(POPULAR);
                    return true;
                } else {
                    toastNoInternet(this);
                    break;
                }

            case R.id.highestRated:
                if(checkNetwork(this)) {
                    getSupportActionBar().setTitle(TOP_RATED_TITLE);
                    HttpAsyncDataTask task = new HttpAsyncDataTask(MainActivity.this);
                    task.execute(TOP_RATED);
                    return true;
                } else
                    toastNoInternet(this);

            case R.id.nowPlaying:
                if(checkNetwork(this)) {
                    getSupportActionBar().setTitle(NOW_PLAYING);
                    HttpAsyncDataTask task = new HttpAsyncDataTask(MainActivity.this);
                    task.execute(INITIAL_ENDPOINT);
                    return true;
                }
                else {
                    toastNoInternet(this);
                    break;
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
        detailViewIntent.putExtra("movieSelected",movieSelected);
        startActivity(detailViewIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    // Uses the HttpAsyncDataTask to handle background HTTP tasks and returns list of Movie objects
    // to give it to the adapter
    @Override
    public void onTaskCompleted(List<Movie> response) {

        movies = response;
        LayoutAnimationController animationController = AnimationUtils
                .loadLayoutAnimation(MainActivity.this,R.anim.grid_translate_up);
        recyclerView.setLayoutAnimation(animationController);

        adapter.updateAdapter(movies, MainActivity.this);
    }

    // Checks the network to see if there is no internet then return a boolean back based on
    // connectivity.
    public boolean checkNetwork (Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public void toastNoInternet (Context context) {
        Toast.makeText(context,"Unable to download list. No internet connection. Wait" +
                        " for internet to connect before trying again",
                Toast.LENGTH_SHORT).show();
    }



}
