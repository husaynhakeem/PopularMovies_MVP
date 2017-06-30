package husaynhakeem.io.popularmovies.database;

import android.net.Uri;

import husaynhakeem.io.popularmovies.provider.MoviesContract;

/**
 * Created by husaynhakeem on 6/30/17.
 */

public class TopRatedMovieTable extends MovieTable {

    public static final Uri CONTENT_URI = MoviesContract.BASE_CONTENT_URI
            .buildUpon()
            .appendEncodedPath(MoviesContract.TOP_RATED_MOVIES_PATH)
            .build();

    public static final String TABLE_NAME = "top_rated_movies";
}
