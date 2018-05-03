package dragons.android.popularmovies.Utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import dragons.android.popularmovies.R;

/**
 * Created by jgebbeken on 4/30/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies = new ArrayList<>();
    private Context context;


    //Constructor with params needed to load data into the adapter

    public MovieAdapter(List<Movie> movies, Context context) {

            this.movies = movies;
            this.context = context;
    }


    public void updateAdapter(List<Movie> movies) {

        this.movies.clear();
        this.movies.addAll(movies);
        this.notifyDataSetChanged();
    }





    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public ImageView leftImageCol;
        //public ImageView rightImageCol;

        public MovieViewHolder(View itemView) {
            super(itemView);

            leftImageCol = itemView.findViewById(R.id.left_column_poster);

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
                .load(movie.getPosterUrl())
                .into(holder.leftImageCol);

    }

    @Override
    public int getItemCount() {

        return movies.size();

    }
}
