package dragons.android.popularmovies;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import dragons.android.popularmovies.Utilities.JSONUtilities;
import dragons.android.popularmovies.Utilities.Movie;
import dragons.android.popularmovies.Utilities.MovieAdapter;
import dragons.android.popularmovies.Utilities.NetworkUtilities;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieClickHandler {

    private final static String INITIAL_ENDPOINT = "now_playing";
    private URL url;
    private String json;
    private TextView test;
    private List<Movie> movies;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    public static final int FIRST_STRING = 0;
    public static final int SPAN_COUNT = 2;
    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";
    public static final String POSTER_URL = "posterUrl";
    public static final String BACK_DROP_URL ="backDropUrl";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "releaseDate";
    public static final String TITLE = "title";
    public static final String VOTE_COUNT = "voteCount";
    public static final String VOTE_AVERAGE = "voteAverage";
    public static final String ID = "id";
    public static final String SMALL_POSTER = "small_poster";

    //SupportActionBar titles
    public static final String NOW_PLAYING =" Now Playing";
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
            new ProcessMovieTask().execute(INITIAL_ENDPOINT);

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
                    new ProcessMovieTask().execute(POPULAR);
                    return true;
                } else {
                    toastNoInternet(this);
                    break;
                }

            case R.id.highestRated:
                if(checkNetwork(this)) {
                    getSupportActionBar().setTitle(TOP_RATED_TITLE);
                    new ProcessMovieTask().execute(TOP_RATED);
                    return true;
                } else
                    toastNoInternet(this);

            case R.id.nowPlaying:
                if(checkNetwork(this)) {
                    getSupportActionBar().setTitle(NOW_PLAYING);
                    new ProcessMovieTask().execute(INITIAL_ENDPOINT);
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

        // Passing data to detail view
        detailViewIntent.putExtra(TITLE,movieSelected.getTitle());
        detailViewIntent.putExtra(BACK_DROP_URL, movieSelected.getBackDropUrl());
        detailViewIntent.putExtra(POSTER_URL, movieSelected.getPosterUrl());
        detailViewIntent.putExtra(OVERVIEW, movieSelected.getOverview());
        detailViewIntent.putExtra(RELEASE_DATE, movieSelected.getReleaseDate());
        detailViewIntent.putExtra(VOTE_COUNT, movieSelected.getVoteCount());
        detailViewIntent.putExtra(VOTE_AVERAGE, movieSelected.getVoteAverage());
        detailViewIntent.putExtra(ID, movieSelected.getId());
        detailViewIntent.putExtra(SMALL_POSTER, movieSelected.getSmallPosterUrl());

        startActivity(detailViewIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());


    }

    public class ProcessMovieTask extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... strings) {

            url = NetworkUtilities.buildUrl(strings[FIRST_STRING]);
            Log.d("URL: ", url.toString());
            try {
                json = NetworkUtilities.response(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            movies = JSONUtilities.movieParsing(json);



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            LayoutAnimationController animationController = AnimationUtils
                    .loadLayoutAnimation(MainActivity.this,R.anim.grid_translate_up);
            recyclerView.setLayoutAnimation(animationController);

            adapter.updateAdapter(movies, MainActivity.this);


        }





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
