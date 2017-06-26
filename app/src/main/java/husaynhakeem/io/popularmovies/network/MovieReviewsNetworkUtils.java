package husaynhakeem.io.popularmovies.network;

import android.content.Context;
import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

import husaynhakeem.io.popularmovies.R;

/**
 * Created by husaynhakeem on 6/26/17.
 */

public class MovieReviewsNetworkUtils {


    private static final String MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String REVIEWS_KEYWORD = "/reviews";
    private static final String API_KEY_PARAM = "api_key";


    public static URL buildReviewsUrl(Context context, int movieId) {

        Uri reviewsUri = Uri.parse(MOVIES_BASE_URL + movieId + REVIEWS_KEYWORD)
                .buildUpon()
                .appendQueryParameter(API_KEY_PARAM, context.getString(R.string.themoviedb_api_key))
                .build();

        try {
            return new URL(reviewsUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
