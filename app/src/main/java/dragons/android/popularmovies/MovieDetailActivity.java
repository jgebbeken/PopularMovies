package dragons.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static dragons.android.popularmovies.MainActivity.BACK_DROP_URL;
import static dragons.android.popularmovies.MainActivity.ID;
import static dragons.android.popularmovies.MainActivity.OVERVIEW;
import static dragons.android.popularmovies.MainActivity.POSTER_URL;
import static dragons.android.popularmovies.MainActivity.RELEASE_DATE;
import static dragons.android.popularmovies.MainActivity.SMALL_POSTER;
import static dragons.android.popularmovies.MainActivity.TITLE;
import static dragons.android.popularmovies.MainActivity.VOTE_AVERAGE;
import static dragons.android.popularmovies.MainActivity.VOTE_COUNT;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String NUM_OF_VOTES = " votes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);


        Intent intent = getIntent();

        // imageURL is not used yet but may use it in the future for a shared resource
        // activity transition
        // id for use with SQL favorites later in project 2.

        String imageUrl = intent.getStringExtra(POSTER_URL);
        String backDropUrl = intent.getStringExtra(BACK_DROP_URL);
        String title = intent.getStringExtra(TITLE);
        String overview = intent.getStringExtra(OVERVIEW);
        int id = intent.getIntExtra(ID,0);
        String releaseDate = intent.getStringExtra(RELEASE_DATE);
        String smallPosterUrl = intent.getStringExtra(SMALL_POSTER);
        int voteCount = intent.getIntExtra(VOTE_COUNT,0);
        double voteAverage = intent.getDoubleExtra(VOTE_AVERAGE, 0);
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
