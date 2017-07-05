package husaynhakeem.io.popularmovies.database;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import husaynhakeem.io.popularmovies.features.MainActivity;
import husaynhakeem.io.popularmovies.models.Movie;

import static husaynhakeem.io.popularmovies.utilities.DbUtils.toContentValues;
import static husaynhakeem.io.popularmovies.utilities.DbUtils.whereArgs;
import static husaynhakeem.io.popularmovies.utilities.DbUtils.whereParam;

/**
 * Created by husaynhakeem on 6/30/17.
 */

public class DbTasks {


    public static final String FAVORITE_INSERT = "favorite_insert";
    public static final String FAVORITE_DELETE = "favorite_delete";
    public static final String FAVORITE_QUERY = "favorite_query";


    public static <T> void executeTask(final Context context, @NonNull final String action, final T... values) {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                switch (action) {

                    case FAVORITE_INSERT:
                        context.getContentResolver()
                                .insert(FavoriteMovieTable.CONTENT_URI, toContentValues((Movie[]) values));
                        break;

                    case FAVORITE_DELETE:
                        context.getContentResolver()
                                .delete(FavoriteMovieTable.CONTENT_URI, whereParam(MovieTable.COLUMN_MOVIE_ID), whereArgs((Movie[]) values));
                        break;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                switch (action) {

                    case FAVORITE_INSERT:
                    case FAVORITE_DELETE:
                        onFavoritesChanged(context);
                        break;

                    default:
                        break;
                }
            }
        }.execute();
    }


    public static Cursor executeQuery(final Context context, @NonNull final String action) {

        switch (action) {

            case FAVORITE_QUERY:
                return context.getContentResolver()
                        .query(FavoriteMovieTable.CONTENT_URI,
                                null,
                                null,
                                null,
                                null);

            default:
                return null;
        }
    }


    private static void onFavoritesChanged(Context context) {
        ((MainActivity) context).onFavoritesChanged();
    }
}
