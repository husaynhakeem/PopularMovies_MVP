package husaynhakeem.io.popularmovies.data;

import android.arch.persistence.room.Entity;
import android.net.Uri;

import husaynhakeem.io.popularmovies.models.Movie;

import static husaynhakeem.io.popularmovies.provider.MoviesContract.BASE_CONTENT_URI;
import static husaynhakeem.io.popularmovies.provider.MoviesContract.TOP_RATED_MOVIES_PATH;

/**
 * Created by husaynhakeem on 6/27/17.
 */

@Entity(tableName = "movie_top_rated")
public class TopRatedMovie extends Movie {

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
            .appendPath(TOP_RATED_MOVIES_PATH)
            .build();

    public static final String TABLE_NAME = "movie_top_rated";
}
