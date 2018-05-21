package dragons.android.popularmovies.Utilities;

import android.content.Context;

import dragons.android.popularmovies.R;

/**
 *  A helper class that gives the List<Object> a sectional header
 */

public class VideoHeader {

    Context context;
    String videoHeader;

    public VideoHeader(){

    }

    public VideoHeader(Context context) {
        this.context = context;
        videoHeader = "Video and Clips";
    }

    public String getVideoHeader() {
        return videoHeader;
    }

}
