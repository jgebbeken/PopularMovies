package dragons.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import dragons.android.popularmovies.Utilities.HttpAsyncDataTask;
import dragons.android.popularmovies.Utilities.ItemClickListener;
import dragons.android.popularmovies.Utilities.Movie;
import dragons.android.popularmovies.Utilities.MovieDetailsAdapter;
import dragons.android.popularmovies.Utilities.RecyclerItemClickListener;
import dragons.android.popularmovies.Utilities.ReviewHeader;
import dragons.android.popularmovies.Utilities.VideoHeader;
import dragons.android.popularmovies.Utilities.Review;
import dragons.android.popularmovies.Utilities.ReviewsAndVideos;
import dragons.android.popularmovies.Utilities.Video;


public class MovieDetailActivity extends AppCompatActivity implements HttpAsyncDataTask.OnTaskCompleted, MovieDetailsAdapter.OnVideoClickHandler {

    private static final String VIDEOS_REVIEW_ENDPOINT = "videosReviews";
    private RecyclerView recyclerView;

    private ArrayList<Object> items;
    Movie detailMovie = new Movie();
    VideoHeader videoHeader = new VideoHeader();
    ReviewHeader reviewHeader = new ReviewHeader();
    private MovieDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_vertical);


        Intent intent = getIntent();
        this.detailMovie = intent.getParcelableExtra("movieSelected");

        String title = detailMovie.getTitle();
        int id = detailMovie.getId();


        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView =  findViewById(R.id.multi_view_recycler);
        items = new ArrayList<>();
        recyclerView.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);



        adapter = new MovieDetailsAdapter(this, items);
        recyclerView.setAdapter(adapter);
        adapter.setOnVideoClickHandler(this);
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
        videoHeader = new VideoHeader(this);
        reviewHeader = new ReviewHeader(this);
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
    }


    @Override
    public void onVideoClick(int position) {
        Video video = (Video) items.get(position);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getVideoUrl()));
        startActivity(intent);
    }
}
