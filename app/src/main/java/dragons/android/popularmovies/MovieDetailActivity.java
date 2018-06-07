package dragons.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dragons.android.popularmovies.data.AppDatabase;
import dragons.android.popularmovies.utilities.AppExecutors;
import dragons.android.popularmovies.utilities.HttpAsyncDataTask;
import dragons.android.popularmovies.models.Movie;
import dragons.android.popularmovies.adapters.MovieDetailsAdapter;
import dragons.android.popularmovies.helpers.ReviewHeader;
import dragons.android.popularmovies.helpers.VideoHeader;
import dragons.android.popularmovies.models.Review;
import dragons.android.popularmovies.utilities.ReviewsAndVideos;
import dragons.android.popularmovies.models.Video;


public class MovieDetailActivity extends AppCompatActivity implements HttpAsyncDataTask.OnTaskCompleted, MovieDetailsAdapter.OnVideoClickHandler,
        MovieDetailsAdapter.OnFavoritesClickHandler {

    private static final String VIDEOS_REVIEW_ENDPOINT = "videosReviews";

    private static final String LAST_POSITION = "lastPosition";
    private static final String BUNDLE_LAYOUT = "layout";

    private Bundle savedInstanceState;
    private ArrayList<Object> items;

    private Movie detailMovie = new Movie();
    private MovieDetailsAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private static boolean doesExist;

    //Database variable
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_vertical);
        this.savedInstanceState = savedInstanceState;

        mDb = AppDatabase.getsInstance(getApplicationContext());


        Intent intent = getIntent();
        this.detailMovie = intent.getParcelableExtra("movieSelected");

        // Need movie name to display on app bar and id to do get reviews and videos.
        String title = detailMovie.getTitle();
        int id = detailMovie.getId();


        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.multi_view_recycler);
        items = new ArrayList<>();
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);



        adapter = new MovieDetailsAdapter(this, items);
        recyclerView.setAdapter(adapter);
        adapter.setOnVideoClickHandler(this);
        adapter.setOnFavoritesClickHandler(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        HttpAsyncDataTask task = new HttpAsyncDataTask(this);
        task.hasId(id);
        task.execute(VIDEOS_REVIEW_ENDPOINT);


    }

    @Override
    public void onTaskCompleted(List<?> response) {

        List<ReviewsAndVideos> rv;
        rv = (List<ReviewsAndVideos>) response;
        items = new ArrayList<>();
        VideoHeader videoHeader = new VideoHeader(this);
        ReviewHeader reviewHeader = new ReviewHeader(this);
        List<Video> videos = new ArrayList<>();
        List<Review> reviews = new ArrayList<>();
        videos.addAll(rv.get(0).getVideos());
        reviews.addAll(rv.get(0).getReviews());

        items.add(detailMovie);
        items.add(videoHeader);
        items.addAll(videos);
        items.add(reviewHeader);
        items.addAll(reviews);

        adapter.updateAdapter(items,this);
        if(savedInstanceState != null) {
            layoutManager.scrollToPosition(savedInstanceState.getInt(LAST_POSITION));
            recyclerView.getLayoutManager().onRestoreInstanceState(savedInstanceState);
        }
    }



    @Override
    public void onVideoClick(int position) {
        Video video = (Video) items.get(position);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getVideoUrl()));
        startActivity(intent);
    }
    @Override
    public void onFavoritesClick(int position) {
        Log.d("Button Test", "It works!");
       final Movie movie = (Movie) items.get(position);
        Log.d("Added object:", "movie");


        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                if(mDb.movieDAO().doesExist(movie.getId())) {
                    mDb.movieDAO().deleteMovie(movie.getId());
                }
                else {
                    mDb.movieDAO().insertMovie(movie);
                }
            }
        });

        adapter.notifyItemChanged(position);
    }


    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        recyclerView.getLayoutManager().onSaveInstanceState();

        int position = layoutManager.findFirstCompletelyVisibleItemPosition();

        outState.putParcelable(BUNDLE_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
        outState.putInt(LAST_POSITION,position);

    }

}
