package dragons.android.popularmovies.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import dragons.android.popularmovies.Helpers.FavoriteHelper;
import dragons.android.popularmovies.Models.Movie;
import dragons.android.popularmovies.R;

/**
 * Purpose of this Adapter is to bind the movie objects to the view holders. This adapter is used
 * in the MainActivity
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies = new ArrayList<>();
    private Context context;
    private OnMovieClickHandler mMovieHandler;
    private OnMovieClickHandler mFavoriteHandler;


    public interface OnMovieClickHandler {
        void onMovieClick (int position);
        void onMainFavoriteClick(int position);
    }

    public void setOnMovieClickHandler(OnMovieClickHandler clickHandler){
        mMovieHandler = clickHandler;
    }

    public void setMainOnFavoriteClickHandler(OnMovieClickHandler clickHandler){
        mFavoriteHandler = clickHandler;
    }


    //Constructor with params needed to load data into the adapter

    public MovieAdapter(List<Movie> movies, Context context) {

            this.movies = movies;
            this.context = context;
    }

    // This is to attempt to refresh the List of movie objects with new JSON data

    public void updateAdapter(List<Movie> movies, Context context) {


        this.movies.clear();
        this.movies.addAll(movies);
        this.context = context;
        notifyDataSetChanged();

    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public final ImageView posterImage;
        public final ImageView ivFavorite;

        public MovieViewHolder(View itemView) {
            super(itemView);

            posterImage = itemView.findViewById(R.id.large_poster);
            ivFavorite = itemView.findViewById(R.id.favoriteStar);
            ivFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mFavoriteHandler != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mMovieHandler.onMainFavoriteClick(position);
                        }
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mMovieHandler != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mMovieHandler.onMovieClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        Movie movie = movies.get(position);

        String movieId = String.valueOf(movie.getId());

        Picasso.get()
                .load(movie.getPosterUrl()).placeholder(R.drawable.tmdb_logo).error(R.drawable.no_image)
                .into(holder.posterImage);


        Picasso.get()
                .load(R.drawable.btn_star_big_off).placeholder(R.drawable.tmdb_logo).error(R.drawable.no_image)
                .into(holder.ivFavorite);
        if(FavoriteHelper.recordExists(movieId,context.getApplicationContext())){
            Picasso.get().load(R.drawable.btn_star_big_on).into(holder.ivFavorite);
        }
        else {
            Picasso.get().load(R.drawable.btn_star_big_off).into(holder.ivFavorite);
        }


    }

    @Override
    public int getItemCount() {

        return movies.size();

    }
}
