package dragons.android.popularmovies.Utilities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import dragons.android.popularmovies.R;

/**
 * Created by jgebbeken on 5/20/2018.
 */

public class ReviewHeaderViewHolder extends RecyclerView.ViewHolder {

    TextView reviewerSection;

    public ReviewHeaderViewHolder(View itemView) {
        super(itemView);

        reviewerSection = itemView.findViewById(R.id.reviewHeader);


    }

    public TextView getReviewerSection() {
        return reviewerSection;
    }

    public void setReviewerSection(TextView reviewerSection) {
        this.reviewerSection = reviewerSection;
    }
}
