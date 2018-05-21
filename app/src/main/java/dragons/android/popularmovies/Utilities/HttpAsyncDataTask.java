package dragons.android.popularmovies.Utilities;


import android.os.AsyncTask;

import android.util.Log;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * HttpAsyncDataTask is designed to handle all HTTP related request, as well as provide
 * JSON parsing. It can take any List of Objects that are related to the models of this app.
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


            //Log.d("URL: ", url.toString());
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
                     Log.d("RV Array", String.valueOf(rv.size()));
                    return rv;
                default:
                    List<Movie> movies = new ArrayList<>();
                    movies.addAll(JSONUtilities.movieParsing(json));
                    Log.d("Movies", String.valueOf(movies.size()));
                    return movies;
            }
        }

        @Override
        protected void onPostExecute(List<?> list) {
            super.onPostExecute(list);
            Log.d("List", String.valueOf(list.size()));
            listener.onTaskCompleted(list);
        }


    public void hasId(int movieId){
        this.movieId = movieId;

    }

    public interface OnTaskCompleted {
        void onTaskCompleted(List<?> response);
    }

}
