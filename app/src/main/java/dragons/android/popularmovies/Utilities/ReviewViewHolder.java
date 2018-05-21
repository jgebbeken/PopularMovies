package dragons.android.popularmovies.Utilities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import dragons.android.popularmovies.R;

/**
 * Created by jgebbeken on 5/20/2018.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    TextView reviewer;
    TextView reviewerContent;


    public ReviewViewHolder(View itemView) {
        super(itemView);

        reviewer = itemView.findViewById(R.id.tvReviewerName);
        reviewerContent = itemView.findViewById(R.id.tvReviewerContent);
    }

    public TextView getReviewer() {
        return reviewer;
    }

    public void setReviewer(TextView reviewer) {
        this.reviewer = reviewer;
    }

    public TextView getReviewerContent() {
        return reviewerContent;
    }

    public void setReviewerContent(TextView reviewerContent) {
        this.reviewerContent = reviewerContent;
    }
}
