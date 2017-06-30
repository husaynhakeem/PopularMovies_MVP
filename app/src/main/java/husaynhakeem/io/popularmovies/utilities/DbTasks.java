package husaynhakeem.io.popularmovies.utilities;

import android.content.Context;
import android.support.annotation.NonNull;

import husaynhakeem.io.popularmovies.data.MovieTable;

/**
 * Created by husaynhakeem on 6/30/17.
 */

public class DbTasks {


    public static final String POPULAR_INSERT = "popular_insert";
    public static final String POPULAR_DELETE = "popular_delete";
    public static final String TOP_RATED_INSERT = "top_rated_insert";
    public static final String TOP_RATED_DELETE = "top_rated_delete";


    public static <T> void executeTask(Context context, @NonNull String action, T... values) {

        switch (action) {

            case POPULAR_INSERT:
                context.getContentResolver().insert(MovieTable.CONTENT_URI, values);
                break;

            case POPULAR_DELETE:
                break;

            case TOP_RATED_INSERT:
                break;

            case TOP_RATED_DELETE:
                break;
        }
    }
}
