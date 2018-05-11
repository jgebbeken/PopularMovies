package dragons.android.popularmovies.Utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by jgebbeken on 5/10/2018.
 */

public class HttpAsyncDataTask extends AsyncTask<String, Void, List<Movie>> {

    private Context context;
    private OnTaskCompleted listener;

    private URL url;
    private String json;
    private List<Movie> movies;


    public HttpAsyncDataTask(OnTaskCompleted activityContext){

        this.listener = activityContext;
    }


        @Override
        protected List<Movie> doInBackground(String... strings) {

            movies = new ArrayList<Movie>();
            url = NetworkUtilities.buildUrl(strings[0]);
            Log.d("URL: ", url.toString());
            try {
                json = NetworkUtilities.response(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            movies = JSONUtilities.movieParsing(json);



            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            listener.onTaskCompleted(movies);


        }

    public interface OnTaskCompleted {
        void onTaskCompleted(List<Movie> response);
    }

}
