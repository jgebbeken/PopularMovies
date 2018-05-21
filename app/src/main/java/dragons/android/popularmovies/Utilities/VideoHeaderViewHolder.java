package dragons.android.popularmovies.Utilities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import dragons.android.popularmovies.R;

/**
 * Created by jgebbeken on 5/20/2018.
 */

public class VideoHeaderViewHolder extends RecyclerView.ViewHolder {

    TextView tvVideoHeader;


    public VideoHeaderViewHolder(View itemView) {
        super(itemView);

        tvVideoHeader = itemView.findViewById(R.id.videoHeader);
    }


    public TextView getTvVideoHeader() {
        return tvVideoHeader;
    }


}
