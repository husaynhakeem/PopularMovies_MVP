package husaynhakeem.io.popularmovies.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import husaynhakeem.io.popularmovies.R;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public class NetworkUtils {


    private static final String MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String PAGE_PARAM = "page";
    private static final String API_KEY_PARAM = "api_key";

    private static final String MOVIES_POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String POSTER_SIZE = "w185";


    public static URL buildMoviesUrl(Context context, String sortOption, String resultsPage) {

        Uri moviesUri = Uri.parse(MOVIES_BASE_URL + sortOption)
                .buildUpon()
                .appendQueryParameter(PAGE_PARAM, resultsPage)
                .appendQueryParameter(API_KEY_PARAM, context.getString(R.string.themoviedb_api_key))
                .build();
        try {
            return new URL(moviesUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static URL buildPosterUrl(String posterPath) {

        Uri posterUri = Uri.parse(MOVIES_POSTER_BASE_URL + POSTER_SIZE + posterPath)
                .buildUpon()
                .build();

        try {
            return new URL(posterUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getResponseFromUrl(URL url) {

        HttpURLConnection httpURLConnection = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return scanner.next();
            }
            return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }
    }


    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
