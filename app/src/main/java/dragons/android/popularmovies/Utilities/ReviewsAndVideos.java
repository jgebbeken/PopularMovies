package dragons.android.popularmovies.Utilities;

import android.util.Log;

import java.util.List;

/** This class is designed to accept both the video and review List objects and create a centralize
 *  location to access from.
 */

public class ReviewsAndVideos {

    private List<Review> reviews;
    private List<Video> videos;

    ReviewsAndVideos(List<Review> reviews, List<Video> videos) {
        this.reviews = reviews;
        this.videos = videos;
    }

    public ReviewsAndVideos() {
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
