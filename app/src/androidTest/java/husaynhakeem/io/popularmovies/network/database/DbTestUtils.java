package husaynhakeem.io.popularmovies.network.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;

import husaynhakeem.io.popularmovies.database.MovieTable;
import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.utilities.DbUtils;

/**
 * Created by husaynhakeem on 7/3/17.
 */

public class DbTestUtils {


    private static Context context = InstrumentationRegistry.getTargetContext();


    static Cursor readMovie(Uri uri, int movieId) {
        return context.getContentResolver()
                .query(uri,
                        null,
                        MovieTable.COLUMN_MOVIE_ID + " = ?",
                        new String[]{String.valueOf(movieId)},
                        null);
    }


    static int deleteMovie(Uri uri, int movieId) {
        return context.getContentResolver()
                .delete(uri,
                        MovieTable.COLUMN_MOVIE_ID + " = ?",
                        new String[]{String.valueOf(movieId)}
                );
    }


    static Uri insertMovie(Uri uri, Movie movie) {
        return context.getContentResolver()
                .insert(uri, DbUtils.toContentValues(new Movie[]{movie}));
    }
}
