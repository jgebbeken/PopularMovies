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

/**
 * This is to provide networking that builds the URL and builds JSON data back with the
 * response method.
 */

public class NetworkUtilities{


    // PLACE YOUR API KEY HERE

    private final static String API_KEY = "";
    private final static String API_PARAM = "api_key";
    private final static String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final static String REGION = "us";
    private final static String REGION_PARAM = "region";


     public static URL buildUrl (String endpoint){

         Uri uri = Uri.parse(BASE_URL).buildUpon()
                 .appendPath(endpoint)
                 .appendQueryParameter(REGION_PARAM,REGION)
                 .appendQueryParameter(API_PARAM, API_KEY)
                 .build();
         URL url;
         try{
             url = new URL(uri.toString());
         }catch (MalformedURLException e) {
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


}
