package dragons.android.popularmovies.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import dragons.android.popularmovies.Helpers.FavoriteHelper;
import dragons.android.popularmovies.Helpers.ReviewHeader;
import dragons.android.popularmovies.Helpers.VideoHeader;
import dragons.android.popularmovies.Models.Movie;
import dragons.android.popularmovies.Models.Review;
import dragons.android.popularmovies.Models.Video;
import dragons.android.popularmovies.R;
import dragons.android.popularmovies.Adapters.ViewHolders.MovieDetailViewHolder;
import dragons.android.popularmovies.Adapters.ViewHolders.ReviewHeaderViewHolder;
import dragons.android.popularmovies.Adapters.ViewHolders.ReviewViewHolder;
import dragons.android.popularmovies.Adapters.ViewHolders.TrailerClipsViewHolder;
import dragons.android.popularmovies.Adapters.ViewHolders.VideoHeaderViewHolder;

/**
 * This is the multi view Movie Detail Adapter. Displays 3 different views (Movie Details,
 * trailers and clips, reviews) and two headers.
 */


public class MovieDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private OnVideoClickHandler onVideoClickHandler;
    private OnFavoritesClickHandler onFavoritesClickHandler;


    private List<Object> items = new ArrayList<>();

    private final int MOVIE_DETAIL = 0;
    private final int TRAILER_CLIPS = 1;
    private final int REVIEWS = 2;
    private final int REVIEW_HEADER = 3;
    private final int VIDEO_HEADER = 4;
    private static final String SITE_FROM = "YouTube";
    private static final String NUM_VOTES = " votes";


    public interface OnVideoClickHandler {
        void onVideoClick (int position);
    }

    public interface OnFavoritesClickHandler {
        void onFavoritesClick( int position);
    }


    public void setOnVideoClickHandler(OnVideoClickHandler clickHandler){
        onVideoClickHandler = clickHandler;
    }

    public void setOnFavoritesClickHandler(OnFavoritesClickHandler clickHandler) {
        onFavoritesClickHandler = clickHandler;
    }


    public MovieDetailsAdapter(Context context, List<Object> items) {

        this.items = items;
        this.context = context;
    }

    public void updateAdapter(List<Object> incoming, Context context) {


        this.items.clear();
        this.items.addAll(incoming);
        this.context = context;
        notifyDataSetChanged();

    }


    @Override
    public int getItemViewType(int position) {

        if (items.get(position) instanceof Movie) {
            return MOVIE_DETAIL;
        } else if (items.get(position) instanceof Video) {
            return TRAILER_CLIPS;
        } else if (items.get(position) instanceof Review) {
            return REVIEWS;
        } else if (items.get(position) instanceof ReviewHeader) {
            return REVIEW_HEADER;
        } else if (items.get(position) instanceof VideoHeader) {
            return VIDEO_HEADER;
        }
        return -1;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case TRAILER_CLIPS:
                View trailerClipsView = inflater.inflate(R.layout.videos_single_column, parent, false);
                viewHolder = new TrailerClipsViewHolder(trailerClipsView, onVideoClickHandler);
                break;
            case REVIEWS:
                View reviewsView = inflater.inflate(R.layout.review_single_column, parent, false);
                viewHolder = new ReviewViewHolder(reviewsView);
                break;
            case REVIEW_HEADER:
                View reviewHeader = inflater.inflate(R.layout.review_header, parent, false);
                viewHolder = new ReviewHeaderViewHolder(reviewHeader);
                break;
            case VIDEO_HEADER:
                View videoHeader = inflater.inflate(R.layout.video_header, parent, false);
                viewHolder = new VideoHeaderViewHolder(videoHeader);
                break;
            default:
                View movieDetailView = inflater.inflate(R.layout.movie_details, parent, false);
                viewHolder = new MovieDetailViewHolder(movieDetailView, onFavoritesClickHandler);
                break;

        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        switch (holder.getItemViewType()) {
            case MOVIE_DETAIL:
                MovieDetailViewHolder movieDetailViewHolder = (MovieDetailViewHolder) holder;
                configMovieViewHolder(movieDetailViewHolder, position);
                break;
            case TRAILER_CLIPS:
                TrailerClipsViewHolder trailerClipsViewHolder = (TrailerClipsViewHolder) holder;
                configTrailerClipsViewHolder(trailerClipsViewHolder, position);
                break;
            case REVIEWS:
                ReviewViewHolder reviewViewHolder = (ReviewViewHolder) holder;
                configReviewViewHolder(reviewViewHolder, position);
                break;
            case REVIEW_HEADER:
                ReviewHeaderViewHolder reviewHeaderViewHolder = (ReviewHeaderViewHolder) holder;
                configReviewerHeaderViewHolder(reviewHeaderViewHolder, position);
                break;
            case VIDEO_HEADER:
                VideoHeaderViewHolder videoHeaderViewHolder = (VideoHeaderViewHolder) holder;
                configVideoHeaderViewHolder(videoHeaderViewHolder, position);
                break;
        }


    }


    @SuppressLint("SetTextI18n")
    private void configMovieViewHolder(MovieDetailViewHolder holder, int position) {

        Movie movie = (Movie) items.get(position);
        // Load images
        Picasso.get().load(movie.getSmallPosterUrl()).into(holder.getIvPoster());
        Picasso.get().load(movie.getBackDropUrl()).into(holder.getIvBackground());

        holder.getRatingBar().setRating(Float.parseFloat(String.valueOf(movie.getVoteAverage())));
        holder.getTvOverview().setText(movie.getOverview());
        holder.getTvReleaseDate().setText(movie.getReleaseDate());
        holder.getTvTitle().setText(movie.getTitle());
        holder.getTvVoteCount().setText(String.valueOf(movie.getVoteCount()) + NUM_VOTES);

        String movieId = String.valueOf(movie.getId());

        if(FavoriteHelper.recordExists(movieId,context.getApplicationContext())){
            Picasso.get().load(R.drawable.btn_star_big_on).into(holder.getIvFavorite());
        }
        else {
            Picasso.get().load(R.drawable.btn_star_big_off).into(holder.getIvFavorite());
        }


    }

    private void configTrailerClipsViewHolder(TrailerClipsViewHolder holder, int position) {


        Video video = (Video) items.get(position);
        Picasso.get().load(video.getThumbnail()).placeholder(R.drawable.tmdb_logo).into(holder.getIvThumbnail());
        holder.getTvYouTubeTitle().setText(video.getVideoName());
        holder.getTvFromSite().setText(SITE_FROM);
        holder.getTvRes().setText(video.getVideoRes());
        holder.getTvType().setText(video.getType());
    }






    private void configReviewViewHolder(ReviewViewHolder holder, int position){
        Review review = (Review) items.get(position);

        holder.getReviewer().setText(review.getAuthor());
        holder.getReviewerContent().setText(review.getContent());
    }

    private void configReviewerHeaderViewHolder(ReviewHeaderViewHolder holder, int position){
        ReviewHeader reviewHeader = (ReviewHeader) items.get(position);
        holder.getReviewerSection().setText(reviewHeader.getReviewHeader());
    }

    private void configVideoHeaderViewHolder(VideoHeaderViewHolder holder, int position){
        VideoHeader videoHeader = (VideoHeader) items.get(position);
        holder.getTvVideoHeader().setText(videoHeader.getVideoHeader());
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

}
