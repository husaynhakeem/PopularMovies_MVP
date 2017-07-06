package husaynhakeem.io.popularmovies.network;

import android.content.Context;
import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

import husaynhakeem.io.popularmovies.R;

/**
 * Created by husaynhakeem on 6/26/17.
 */

public class MovieTrailersNetworkUtils {


    private static final String MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String TRAILERS_KEYWORD = "/videos";
    private static final String API_KEY_PARAM = "api_key";

    private static final String YOUTUBE_BASE_URL = "https://www.youtube.com";
    private static final String YOUTUBE_WATCH_KEYWORD = "watch";
    private static final String YOUTUBE_VIDEO_PARAMETER_KEY = "v";

    private static final String YOUTUBE_BASE_URI = "vnd.youtube:";


    public static URL buildTrailersUrl(Context context, int movieId) {

        Uri trailersUri = Uri.parse(MOVIES_BASE_URL + movieId + TRAILERS_KEYWORD)
                .buildUpon()
                .appendQueryParameter(API_KEY_PARAM, context.getString(R.string.themoviedb_api_key))
                .build();

        try {
            return new URL(trailersUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Uri buildYoutubeTrailerUri(String trailerKey) {
        return Uri.parse(YOUTUBE_BASE_URI + trailerKey);
    }


    public static String buildYoutubeTrailerUrl(String trailerKey) {

        Uri youtubeTrailerUri = Uri.parse(YOUTUBE_BASE_URL)
                .buildUpon()
                .appendEncodedPath(YOUTUBE_WATCH_KEYWORD)
                .appendQueryParameter(YOUTUBE_VIDEO_PARAMETER_KEY, trailerKey)
                .build();
        return youtubeTrailerUri.toString();
    }
}
