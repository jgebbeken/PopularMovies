package dragons.android.popularmovies.Utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import dragons.android.popularmovies.R;

/**
 * Created by jgebbeken on 5/15/2018.
 */

public class TrailersClipsAdapter extends RecyclerView.Adapter<TrailersClipsAdapter.TrailersClipsViewHolder> {


    private static final int FIRST_INDEX = 0;
    List<ReviewsAndVideos> rv = new ArrayList<>();
    List<Video> videos = new ArrayList<>();
    Context context;

    private int mRowIndex = -1;

    public TrailersClipsAdapter (List<ReviewsAndVideos> rv, Context context) {

        this.rv = rv;
        this.context = context;
        this.videos = this.rv.get(FIRST_INDEX).getVideos();

    }

    public void setRowIndex(int index) {
        mRowIndex = index;
    }


    public class TrailersClipsViewHolder extends RecyclerView.ViewHolder {



        public TrailersClipsViewHolder(View itemView) {
            super(itemView);
        }

        TextView tvYouTubeTitle = itemView.findViewById(R.id.tvYouTubeTitle);
        TextView tvVideoType = itemView.findViewById(R.id.tvVideoType);
        TextView tvVideoFromSite = itemView.findViewById(R.id.tvVideoFromSite);
        TextView tvVideoRes = itemView.findViewById(R.id.tvVideoRes);

        ImageView ivThumbnail = itemView.findViewById(R.id.ivThumbnail);

    }




    // This will update the adapter as soon as new data comes in
    public void updateAdapter(List<ReviewsAndVideos> rv, Context context) {


        this.rv.clear();
        this.rv.addAll(rv);
        this.context = context;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TrailersClipsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videos_single_column,
                parent,false);
        TrailersClipsViewHolder trailersClipsViewHolder = new TrailersClipsViewHolder(view);

        return trailersClipsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersClipsViewHolder holder, int position) {

        Video video = videos.get(position);

        holder.tvYouTubeTitle.setText(video.getVideoName());

        // Must be removed later once you have it set in the model
        holder.tvVideoFromSite.setText("YouTube");
        holder.tvVideoRes.setText(video.getVideoRes());
        holder.tvVideoType.setText(video.getType());
        Picasso.get().load(video.getThumbnail()).placeholder(R.drawable.tmdb_logo)
                .error(R.drawable.no_image).into(holder.ivThumbnail);



    }

    @Override
    public int getItemCount() {
        return videos.size();
    }
}
