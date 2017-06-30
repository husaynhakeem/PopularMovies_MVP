package husaynhakeem.io.popularmovies.database;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import husaynhakeem.io.popularmovies.models.Movie;

import static husaynhakeem.io.popularmovies.utilities.DbUtils.toContentValuesArray;
import static husaynhakeem.io.popularmovies.utilities.DbUtils.whereArgs;

/**
 * Created by husaynhakeem on 6/30/17.
 */

public class DbTasks {


    public static final String POPULAR_BULK_INSERT = "popular_insert";
    public static final String POPULAR_DELETE = "popular_delete";
    public static final String TOP_RATED_BULK_INSERT = "top_rated_insert";
    public static final String TOP_RATED_DELETE = "top_rated_delete";
    public static final String POPULAR_BULK_DELETE = "popular_bulk_delete";
    public static final String TOP_RATED_BULK_DELETE = "top_rated_bulk_delete";


    public static <T> void executeTask(final Context context, @NonNull final String action, final T... values) {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                switch (action) {

                    case POPULAR_BULK_INSERT:
                        context.getContentResolver()
                                .bulkInsert(PopularMovieTable.CONTENT_URI, toContentValuesArray((Movie[]) values));
                        break;

                    case POPULAR_DELETE:
                        context.getContentResolver()
                                .delete(PopularMovieTable.CONTENT_URI, MovieTable.COLUMN_MOVIE_ID, whereArgs((Movie[]) values));
                        break;

                    case POPULAR_BULK_DELETE:
                        context.getContentResolver()
                                .delete(PopularMovieTable.CONTENT_URI, null, null);
                        break;

                    case TOP_RATED_BULK_INSERT:
                        context.getContentResolver()
                                .bulkInsert(TopRatedMovieTable.CONTENT_URI, toContentValuesArray((Movie[]) values));
                        break;

                    case TOP_RATED_DELETE:
                        context.getContentResolver()
                                .delete(TopRatedMovieTable.CONTENT_URI, MovieTable.COLUMN_MOVIE_ID, whereArgs((Movie[]) values));
                        break;

                    case TOP_RATED_BULK_DELETE:
                        context.getContentResolver()
                                .delete(TopRatedMovieTable.CONTENT_URI, null, null);
                        break;
                }

                return null;
            }
        }.execute();
    }
}
