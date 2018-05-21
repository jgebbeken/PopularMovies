package dragons.android.popularmovies.Utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import dragons.android.popularmovies.R;

/**
 * Created by jgebbeken on 5/20/2018.
 */

public class MovieDetailViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivBackground;
    private ImageView ivPoster;
    private TextView tvTitle;
    private TextView tvOverview;
    private TextView tvReleaseDate;
    private TextView tvVoteCount;
    private RatingBar ratingBar;





    public MovieDetailViewHolder(View itemView) {
        super(itemView);

        ivBackground = itemView.findViewById(R.id.ivBackground);
        ivPoster = itemView.findViewById(R.id.ivDetailViewPoster);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvOverview = itemView.findViewById(R.id.tvOverview);
        tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
        tvVoteCount = itemView.findViewById(R.id.tvVoteCount);
        ratingBar = itemView.findViewById(R.id.ratingBar);


    }


    public ImageView getIvBackground() {
        return ivBackground;
    }

    public void setIvBackground(ImageView ivBackground) {
        this.ivBackground = ivBackground;
    }

    public ImageView getIvPoster() {
        return ivPoster;
    }

    public void setIvPoster(ImageView ivPoster) {
        this.ivPoster = ivPoster;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    public TextView getTvOverview() {
        return tvOverview;
    }

    public void setTvOverview(TextView tvOverview) {
        this.tvOverview = tvOverview;
    }

    public TextView getTvReleaseDate() {
        return tvReleaseDate;
    }

    public void setTvReleaseDate(TextView tvReleaseDate) {
        this.tvReleaseDate = tvReleaseDate;
    }

    public TextView getTvVoteCount() {
        return tvVoteCount;
    }

    public void setTvVoteCount(TextView tvVoteCount) {
        this.tvVoteCount = tvVoteCount;
    }

    public RatingBar getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(RatingBar ratingBar) {
        this.ratingBar = ratingBar;
    }
}