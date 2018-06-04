package dragons.android.popularmovies.Adapters.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import dragons.android.popularmovies.R;

/**
 *  Used in Movie Detail Recycler returns viewHolders to the controller.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    private final TextView reviewer;
    private final TextView reviewerContent;


    public ReviewViewHolder(View itemView) {
        super(itemView);

        reviewer = itemView.findViewById(R.id.tvReviewerName);
        reviewerContent = itemView.findViewById(R.id.tvReviewerContent);
    }

    public TextView getReviewer() {
        return reviewer;
    }

    public TextView getReviewerContent() {
        return reviewerContent;
    }

}
