package husaynhakeem.io.popularmovies.network;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by husaynhakeem on 6/26/17.
 */

public class MoviePosterNetworkUtils {


    private static final String MOVIES_POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String POSTER_SIZE = "w185";


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
}
