package husaynhakeem.io.popularmovies.network;

import android.content.Context;
import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

import husaynhakeem.io.popularmovies.R;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public class MoviesNetworkUtils {


    private static final String MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String PAGE_PARAM = "page";
    private static final String API_KEY_PARAM = "api_key";


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
}
