package dragons.android.popularmovies.Utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import dragons.android.popularmovies.R;

/**
 * Created by jgebbeken on 5/20/2018.
 */

public class TrailerClipsViewHolder extends RecyclerView.ViewHolder {


    private ImageView ivThumbnail;
    private TextView tvYouTubeTitle;
    private TextView tvType;
    private TextView tvRes;
    private TextView tvFromSite;
    private Context context;


    public TrailerClipsViewHolder(View itemView, final MovieDetailsAdapter.OnVideoClickHandler onVideoClickHandler) {
        super(itemView);

        ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
        tvYouTubeTitle = itemView.findViewById(R.id.tvYouTubeTitle);
        tvType = itemView.findViewById(R.id.tvVideoType);
        tvFromSite = itemView.findViewById(R.id.tvVideoFromSite);
        tvRes = itemView.findViewById(R.id.tvVideoRes);
        this.context = context;

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

    public void setIvThumbnail(ImageView ivThumbnail) {
        this.ivThumbnail = ivThumbnail;
    }

    public TextView getTvYouTubeTitle() {
        return tvYouTubeTitle;
    }

    public void setTvYouTubeTitle(TextView tvYouTubeTitle) {
        this.tvYouTubeTitle = tvYouTubeTitle;
    }

    public TextView getTvType() {
        return tvType;
    }

    public void setTvType(TextView tvType) {
        this.tvType = tvType;
    }

    public TextView getTvRes() {
        return tvRes;
    }

    public void setTvRes(TextView tvRes) {
        this.tvRes = tvRes;
    }

    public TextView getTvFromSite() {
        return tvFromSite;
    }

    public void setTvFromSite(TextView tvFromSite) {
        this.tvFromSite = tvFromSite;
    }



}
