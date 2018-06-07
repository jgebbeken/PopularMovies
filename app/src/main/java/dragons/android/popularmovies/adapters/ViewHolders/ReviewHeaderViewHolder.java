package dragons.android.popularmovies.adapters.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import dragons.android.popularmovies.R;

/**
 * Header used in separating data in the Movie Detail recycler
 */

public class ReviewHeaderViewHolder extends RecyclerView.ViewHolder {

    private final TextView reviewerSection;

    public ReviewHeaderViewHolder(View itemView) {
        super(itemView);

        reviewerSection = itemView.findViewById(R.id.reviewHeader);


    }

    public TextView getReviewerSection() {
        return reviewerSection;
    }

}
