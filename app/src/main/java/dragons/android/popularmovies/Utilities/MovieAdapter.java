package dragons.android.popularmovies.Utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import dragons.android.popularmovies.R;

/**
 * Purpose of this Adapter is to bind the movie objects to the view holders.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies = new ArrayList<>();
    private Context context;
    private OnMovieClickHandler mMovieHandler;


    public interface OnMovieClickHandler {
        void onMovieClick (int position);
    }

    public void setOnMovieClickHandler(OnMovieClickHandler clickHandler){
        mMovieHandler = clickHandler;
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

        public ImageView posterImage;
        //public ImageView rightImageCol;

        public MovieViewHolder(View itemView) {
            super(itemView);

            posterImage = itemView.findViewById(R.id.large_poster);

            itemView. setOnClickListener(new View.OnClickListener() {
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

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item,parent,false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        Movie movie = movies.get(position);

        Picasso.get()
                .load(movie.getPosterUrl()).placeholder(R.drawable.tmdb_logo).error(R.drawable.no_image)
                .into(holder.posterImage);




    }

    @Override
    public int getItemCount() {

        return movies.size();

    }
}
