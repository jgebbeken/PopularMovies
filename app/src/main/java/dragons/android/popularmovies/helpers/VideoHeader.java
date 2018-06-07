package dragons.android.popularmovies.helpers;


import android.content.Context;

import dragons.android.popularmovies.R;

/**
 *  A helper class that gives the List<Object> a sectional header
 */

public class VideoHeader {

    private final String videoHeader;


    public VideoHeader(Context context) {

        videoHeader = context.getResources().getString(R.string.video_header);
    }

    public String getVideoHeader() {
        return videoHeader;
    }

}
