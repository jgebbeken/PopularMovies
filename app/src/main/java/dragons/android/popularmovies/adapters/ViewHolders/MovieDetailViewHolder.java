package dragons.android.popularmovies.adapters.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import dragons.android.popularmovies.adapters.MovieDetailsAdapter;
import dragons.android.popularmovies.R;

/**
 * Used in Movie Detail Recycler handles onClick and returning viewHolders to the controller.
 */

public class MovieDetailViewHolder extends RecyclerView.ViewHolder {

    private final ImageView ivBackground;
    private final ImageView ivPoster;
    private final ImageView ivFavorite;
    private final TextView tvTitle;
    private final TextView tvOverview;
    private final TextView tvReleaseDate;
    private final TextView tvVoteCount;
    private final RatingBar ratingBar;





    public MovieDetailViewHolder(View itemView, final MovieDetailsAdapter.OnFavoritesClickHandler onFavoritesClickHandler) {
        super(itemView);

        ivBackground = itemView.findViewById(R.id.ivBackground);
        ivPoster = itemView.findViewById(R.id.ivDetailViewPoster);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvOverview = itemView.findViewById(R.id.tvOverview);
        tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
        tvVoteCount = itemView.findViewById(R.id.tvVoteCount);
        ratingBar = itemView.findViewById(R.id.ratingBar);
        ivFavorite = itemView.findViewById(R.id.ivFavorite);
        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onFavoritesClickHandler != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        onFavoritesClickHandler.onFavoritesClick(position);
                    }
                }
            }
        });
    }


    public ImageView getIvFavorite(){
        return ivFavorite;
    }


    public ImageView getIvBackground() {
        return ivBackground;
    }


    public ImageView getIvPoster() {
        return ivPoster;
    }


    public TextView getTvTitle() {
        return tvTitle;
    }


    public TextView getTvOverview() {
        return tvOverview;
    }


    public TextView getTvReleaseDate() {
        return tvReleaseDate;
    }


    public TextView getTvVoteCount() {
        return tvVoteCount;
    }


    public RatingBar getRatingBar() {
        return ratingBar;
    }

}