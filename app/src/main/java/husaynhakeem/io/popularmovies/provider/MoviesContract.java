package husaynhakeem.io.popularmovies.provider;

import android.net.Uri;

/**
 * Created by husaynhakeem on 6/27/17.
 */

public class MoviesContract {

    public static final String CONTENT_AUTHORITY = "husaynhakeem.io.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String FAVORITE_MOVIES_PATH = "favorite";
}
