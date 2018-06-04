package dragons.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import dragons.android.popularmovies.Contracts.MovieFavoritesContract;
import dragons.android.popularmovies.Helpers.FavoriteHelper;
import dragons.android.popularmovies.Utilities.HttpAsyncDataTask;
import dragons.android.popularmovies.Models.Movie;
import dragons.android.popularmovies.Adapters.MovieDetailsAdapter;
import dragons.android.popularmovies.Helpers.ReviewHeader;
import dragons.android.popularmovies.Helpers.VideoHeader;
import dragons.android.popularmovies.Models.Review;
import dragons.android.popularmovies.Utilities.ReviewsAndVideos;
import dragons.android.popularmovies.Models.Video;


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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_vertical);
        this.savedInstanceState = savedInstanceState;

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
        Movie movie = (Movie) items.get(position);
        Log.d("Added object:", "movie");
       String movieId = String.valueOf(movie.getId());

       if(FavoriteHelper.recordExists(movieId,this)){
           getContentResolver().delete(MovieFavoritesContract.MovieFavoritesEntry.CONTENT_URI.buildUpon().appendPath(movieId)
                   .build(),
                   MovieFavoritesContract.MovieFavoritesEntry.MOVIE_ID, new String[]{String.valueOf(movie.getId())});
           Picasso.get().load(R.drawable.btn_star_big_off).into((ImageView) findViewById(R.id.ivFavorite));
       }
       else{
           //dbHelper.addFavoriteMovie(movie);
           FavoriteHelper.addFavorites(movie,this);
           Picasso.get().load(R.drawable.btn_star_big_on).into((ImageView) findViewById(R.id.ivFavorite));
       }



    }


    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        recyclerView.getLayoutManager().onSaveInstanceState();

        int position = layoutManager.findFirstCompletelyVisibleItemPosition();

        outState.putParcelable(BUNDLE_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
        outState.putInt(LAST_POSITION,position);

    }

}
