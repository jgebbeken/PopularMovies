package dragons.android.popularmovies.adapters.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import dragons.android.popularmovies.adapters.MovieDetailsAdapter;
import dragons.android.popularmovies.R;

/**
 * Used in Movie Detail Recycler handles onClick and returning viewHolders to the controller.
 */

public class TrailerClipsViewHolder extends RecyclerView.ViewHolder {


    private final ImageView ivThumbnail;
    private final TextView tvYouTubeTitle;
    private final TextView tvType;
    private final TextView tvRes;
    private final TextView tvFromSite;


    public TrailerClipsViewHolder(View itemView, final MovieDetailsAdapter.OnVideoClickHandler onVideoClickHandler) {
        super(itemView);

        ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
        tvYouTubeTitle = itemView.findViewById(R.id.tvYouTubeTitle);
        tvType = itemView.findViewById(R.id.tvVideoType);
        tvFromSite = itemView.findViewById(R.id.tvVideoFromSite);
        tvRes = itemView.findViewById(R.id.tvVideoRes);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onVideoClickHandler != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        onVideoClickHandler.onVideoClick(position);
                    }
                }
            }
        });
    }



    public ImageView getIvThumbnail() {
        return ivThumbnail;
    }

    public TextView getTvYouTubeTitle() {
        return tvYouTubeTitle;
    }

    public TextView getTvType() {
        return tvType;
    }

    public TextView getTvRes() {
        return tvRes;
    }

    public TextView getTvFromSite() {
        return tvFromSite;
    }


}
