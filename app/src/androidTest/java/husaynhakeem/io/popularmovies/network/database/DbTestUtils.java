package husaynhakeem.io.popularmovies.network.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;

import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.utilities.DbUtils;

import static husaynhakeem.io.popularmovies.database.FavoriteMovieTable.COLUMN_MOVIE_ID;

/**
 * Created by husaynhakeem on 7/3/17.
 */

public class DbTestUtils {


    private static Context context = InstrumentationRegistry.getTargetContext();


    static Cursor readMovie(Uri uri) {
        return context.getContentResolver()
                .query(uri,
                        null,
                        null,
                        null,
                        null);
    }


    static Cursor readMovie(Uri uri, int movieId) {
        return context.getContentResolver()
                .query(uri,
                        null,
                        COLUMN_MOVIE_ID + " = ?",
                        new String[]{String.valueOf(movieId)},
                        null);
    }


    static int deleteMovie(Uri uri, int movieId) {
        return context.getContentResolver()
                .delete(uri,
                        COLUMN_MOVIE_ID + " = ?",
                        new String[]{String.valueOf(movieId)}
                );
    }


    static Uri insertMovie(Uri uri, Movie movie) {
        return context.getContentResolver()
                .insert(uri, DbUtils.toContentValues(new Movie[]{movie}));
    }


    static int bulkInsertMovies(Uri uri, Movie[] movies) {
        return context.getContentResolver().bulkInsert(uri, DbUtils.toContentValuesArray(movies));
    }
}
