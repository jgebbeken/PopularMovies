package dragons.android.popularmovies.Utilities;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import dragons.android.popularmovies.BuildConfig;

/**
 * This is to provide networking that builds the URL and builds JSON data back with the
 * response method.
 */

class NetworkUtilities{


    // THE API

    private static final String API_KEY = BuildConfig.TheMovieDBKey;
    private static final String API_PARAM = "api_key";
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String REGION = "us";
    private static final String REGION_PARAM = "region";
    private static final String APPEND_PARAM = "append_to_response";
    private static final String APPEND ="videos,reviews";
    private static final String PAGE_PARAM = "page";

    private static String urlSelected;




     public static URL buildUrl (int movieId){


         urlSelected = BASE_URL + movieId;
         Uri uri;
         uri = buildUri(urlSelected);
         URL url;

         try{
             url = new URL(uri.toString());
         }catch (MalformedURLException e) {
             e.printStackTrace();
             return null;
         }

        return url;
    }


    public static URL buildUrl (String endpoint){



        urlSelected = BASE_URL;
        Uri uri;
        uri = buildUri(endpoint,urlSelected);
        URL url;

        try{
            url = new URL(uri.toString());
        }catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        return url;
    }

    public static URL buildUrl (String endpoint,String page){
        urlSelected = BASE_URL;
        Uri uri;
        uri = buildUri(endpoint,urlSelected,page);
        URL url;
        try {
            url = new URL(uri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
            return null;
        }


        return url;
    }


    public static String response (URL url) throws IOException {



        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(5000);

        StringBuilder json = new StringBuilder();

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try {
                InputStream input = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));

                String currentLine;

                while ((currentLine = bufferedReader.readLine()) != null) {

                    json.append(currentLine);
                }

                Log.d("JSON: ", json.toString());



            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }

        }
        return json.toString();
    }



    private static Uri buildUri (String endpoint, String mBaseUrl) {


        return Uri.parse(mBaseUrl).buildUpon()
                .appendPath(endpoint)
                .appendQueryParameter(REGION_PARAM, REGION)
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();
    }

    private static Uri buildUri (String mBaseUrl) {

        return Uri.parse(mBaseUrl).buildUpon()
                .appendQueryParameter(REGION_PARAM, REGION)
                .appendQueryParameter(API_PARAM,API_KEY)
                .appendQueryParameter(APPEND_PARAM,APPEND)
                .build();
    }

    private static Uri buildUri (String endpoint, String mBaseUrl, String page){
        return Uri.parse(mBaseUrl).buildUpon()
                .appendPath(endpoint)
                .appendQueryParameter(REGION_PARAM,REGION)
                .appendQueryParameter(API_PARAM,API_KEY)
                .appendQueryParameter(PAGE_PARAM, page)
                .build();
    }
}
