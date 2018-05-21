package dragons.android.popularmovies.Utilities;

import android.content.Context;

import dragons.android.popularmovies.R;

/**
 * Helper class to give List<Object> items sections
 */

public class ReviewHeader {

    String reviewHeader;
    Context context;

    public ReviewHeader() {
    }

    public ReviewHeader(Context context) {
        this.context = context;
        reviewHeader = "Reviews";
    }

    public String getReviewHeader() {
        return reviewHeader;
    }
}
