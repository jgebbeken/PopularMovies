package dragons.android.popularmovies.Helpers;


import android.content.Context;

import dragons.android.popularmovies.R;

/**
 * Helper class to give List<Object> items sections
 */

public class ReviewHeader {

    private final String reviewHeader;


    public ReviewHeader(Context context) {

        reviewHeader = context.getResources().getString(R.string.review_header);
    }

    public String getReviewHeader() {
        return reviewHeader;
    }
}
