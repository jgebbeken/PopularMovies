package dragons.android.popularmovies.utilities;

import java.util.List;

import dragons.android.popularmovies.models.Review;
import dragons.android.popularmovies.models.Video;

/** This class is designed to accept both the video and review List objects and create a centralize
 *  location to access from.
 */

public class ReviewsAndVideos {

    private final List<Review> reviews;
    private final List<Video> videos;

    ReviewsAndVideos(List<Review> reviews, List<Video> videos) {
        this.reviews = reviews;
        this.videos = videos;
    }


    public List<Review> getReviews() {
        return reviews;
    }



    public List<Video> getVideos() {
        return videos;
    }

}
