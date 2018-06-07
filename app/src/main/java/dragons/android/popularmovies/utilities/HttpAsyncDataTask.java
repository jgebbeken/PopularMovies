package dragons.android.popularmovies.utilities;


import android.os.AsyncTask;

import android.util.Log;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import dragons.android.popularmovies.models.Movie;


/**
 * HttpAsyncDataTask is designed to handle all HTTP related request, as well as provide
 * JSON parsing. It can take any List of Objects that are related to the models of this app.
 */

public class HttpAsyncDataTask extends AsyncTask<String, Void, List<?>> {

    private final OnTaskCompleted listener;

    private String json;
    private int movieId;



    public HttpAsyncDataTask(OnTaskCompleted activityContext){

        this.listener = activityContext;


    }


        @Override
        protected List<?> doInBackground(String... strings) {

            String page = "";
            String endpoint = strings[0];
            if(strings.length > 1){
                page = strings[1];
            }

            URL url;
            switch (endpoint){
                case "videosReviews":
                    url = NetworkUtilities.buildUrl(movieId);
                    break;
                default:
                    if(page.equalsIgnoreCase("")) {
                        url = NetworkUtilities.buildUrl(strings[0]);
                    }
                    else {
                        url = NetworkUtilities.buildUrl(endpoint,page);
                    }
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

                    if (movies !=null) {
                        return movies;
                    } else {
                        movies = new ArrayList<>();
                        return movies;
                    }
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
