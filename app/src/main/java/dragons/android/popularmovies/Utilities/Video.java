package dragons.android.popularmovies.Utilities;

/**
 * Created by jgebbeken on 5/13/2018.
 */

public class Video {


    private static final String YOUTUBE_BASE_URL ="https://www.youtube.com/watch?v=";
    private static final String YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/";
    private static final String YOUTUBE_THUMBNAIL_ENDPOINT = "/hqdefault.jpg";

    private String videoName;
    private String videoRes;
    private String type;
    private String videoUrl;
    private String key;

    public Video() {
    }

    public Video(String videoName, String videoRes, String type, String videoUrl) {
        this.videoName = videoName;
        this.videoRes = videoRes;
        this.type = type;
        this.videoUrl = videoUrl;
    }

    public String getVideoName() {
        return videoName;
    }


    public String getVideoRes() {
        return videoRes;
    }

    public String getType() {
        return type;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoRes(String videoRes) {
        this.videoRes = videoRes;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVideoUrl(String key) {
        this.key = key;
        this.videoUrl = YOUTUBE_BASE_URL + key;
    }

    public String getThumbnail() {
        return YOUTUBE_THUMBNAIL_URL + key + YOUTUBE_THUMBNAIL_ENDPOINT;
    }
}
