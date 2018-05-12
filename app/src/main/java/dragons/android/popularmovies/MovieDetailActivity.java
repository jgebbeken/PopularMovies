package dragons.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dragons.android.popularmovies.Utilities.Movie;


public class MovieDetailActivity extends AppCompatActivity {

    private static final String NUM_OF_VOTES = " votes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);


        Intent intent = getIntent();
        Movie detailMovie = intent.getParcelableExtra("movieSelected");

        // imageURL is not used yet but may use it in the future for a shared resource
        // activity transition
        // id for use with SQL favorites later in project 2.

        String imageUrl = detailMovie.getPosterUrl();
        String backDropUrl = detailMovie.getBackDropUrl();
        String title = detailMovie.getTitle();
        String overview = detailMovie.getOverview();
        int id = detailMovie.getId();
        String releaseDate = detailMovie.getReleaseDate();
        String smallPosterUrl = detailMovie.getSmallPosterUrl();
        int voteCount = detailMovie.getVoteCount();
        double voteAverage = detailMovie.getVoteAverage();
        float voteRating = (float) voteAverage;




        ImageView ivBackground = findViewById(R.id.ivBackground);
        ImageView ivPoster = findViewById(R.id.ivDetailViewPoster);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvOverview = findViewById(R.id.tvOverview);
        TextView tvReleaseDate = findViewById(R.id.tvReleaseDate);
        TextView tvVoteCount = findViewById(R.id.tvVoteCount);
        RatingBar ratingBar = findViewById(R.id.ratingBar);


        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        // Load images
        Picasso.get().load(smallPosterUrl).into(ivPoster);
        Picasso.get().load(backDropUrl).into(ivBackground);




        // Populate UI TextViews and Rating bar controls
        tvTitle.setText(title);
        tvReleaseDate.setText(releaseDate);
        tvVoteCount.setText(String.valueOf(voteCount) + NUM_OF_VOTES);
        tvOverview.setText(overview);
        ratingBar.setIsIndicator(true);
        ratingBar.setStepSize(0.1f);
        ratingBar.setRating(voteRating);

    }
}
