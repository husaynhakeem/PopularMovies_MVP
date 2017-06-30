package husaynhakeem.io.popularmovies.database;

import android.provider.BaseColumns;

/**
 * Created by husaynhakeem on 6/30/17.
 */

public class MovieTable implements BaseColumns {

    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_MOVIE_TITLE = "movie_title";
    public static final String COLUMN_MOVIE_POSTER = "movie_poster";
    public static final String COLUMN_MOVIE_RELEASE_DATE = "movie_date";
    public static final String COLUMN_MOVIE_VOTE_AVERAGE = "movie_vote_average";
    public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";
}
