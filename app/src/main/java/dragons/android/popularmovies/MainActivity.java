package dragons.android.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import dragons.android.popularmovies.Utilities.JSONUtilities;
import dragons.android.popularmovies.Utilities.Movie;
import dragons.android.popularmovies.Utilities.MovieAdapter;
import dragons.android.popularmovies.Utilities.NetworkUtilities;

public class MainActivity extends AppCompatActivity {

    private final static String INITIAL_ENDPOINT = "now_playing";
    private URL url;
    private String json;
    private TextView test;
    private List<Movie> movies;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private final static int FIRST_STRING = 0;
    private final static int SPAN_COUNT = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<>();

        // Setup the recycler view with the GirdLayoutManger that spans two columns.
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,SPAN_COUNT));
        adapter = new MovieAdapter(movies,MainActivity.this);


        // We need to do the networking outside the main thread. JSON is downloaded outside the
        // main thread as well.
        new ProcessMovieTask().execute(INITIAL_ENDPOINT);




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

            Log.d("MAIN_ACTIVITY", movies.get(1).getOverview());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            recyclerView.setAdapter(adapter);

        }

    }
}
