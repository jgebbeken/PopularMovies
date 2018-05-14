package dragons.android.popularmovies.Utilities;


import android.os.AsyncTask;

import android.util.Log;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by jgebbeken on 5/10/2018.
 */

public class HttpAsyncDataTask extends AsyncTask<String, Void, List<?>> {

    private OnTaskCompleted listener;

    private String json;
    private int movieId;



    public HttpAsyncDataTask(OnTaskCompleted activityContext){

        this.listener = activityContext;


    }


        @Override
        protected List<?> doInBackground(String... strings) {


            String endpoint = strings[0];

            URL url;
            switch (endpoint){
                case "videosReviews":
                    url = NetworkUtilities.buildUrl(movieId);
                    break;
                default:
                    url = NetworkUtilities.buildUrl(strings[0]);
            }


            Log.d("URL: ", url.toString());
            try {
                json = NetworkUtilities.response(url);
                Log.d("RV JSON: ", json);
            } catch (IOException e) {
                e.printStackTrace();
            }

            switch (endpoint) {
                case "videosReviews":
                    List<ReviewsAndVideos> rv = new ArrayList<>();
                     rv.add(JSONUtilities.videoAndReviewParsing(json));
                    return rv;
                default:
                    List<Movie> movies;
                    movies = JSONUtilities.movieParsing(json);
                    return movies;
            }
        }

        @Override
        protected void onPostExecute(List<?> list) {
            super.onPostExecute(list);
            listener.onTaskCompleted(list);
        }


    public void hasId(int movieId){
        this.movieId = movieId;
        return;
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(List<?> response);
    }

}
