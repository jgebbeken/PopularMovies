package dragons.android.popularmovies.Adapters.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import dragons.android.popularmovies.R;

/**
 *  Header VieHolder use to separate data in the recycler
 */

public class VideoHeaderViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvVideoHeader;


    public VideoHeaderViewHolder(View itemView) {
        super(itemView);

        tvVideoHeader = itemView.findViewById(R.id.videoHeader);
    }


    public TextView getTvVideoHeader() {
        return tvVideoHeader;
    }


}
