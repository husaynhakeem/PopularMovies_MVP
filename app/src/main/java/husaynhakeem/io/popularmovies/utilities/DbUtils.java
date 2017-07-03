package husaynhakeem.io.popularmovies.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import husaynhakeem.io.popularmovies.database.MovieTable;
import husaynhakeem.io.popularmovies.database.PopularMovieTable;
import husaynhakeem.io.popularmovies.database.TopRatedMovieTable;
import husaynhakeem.io.popularmovies.models.Movie;

/**
 * Created by husaynhakeem on 6/27/17.
 */

public class DbUtils {


    public static ContentValues toContentValues(Movie[] movies) {

        if (movies == null || movies.length == 0)
            return null;

        Movie movie = movies[0];

        ContentValues values = new ContentValues();
        values.put(MovieTable.COLUMN_MOVIE_ID, movie.getId());
        values.put(MovieTable.COLUMN_MOVIE_TITLE, movie.getTitle());
        values.put(MovieTable.COLUMN_MOVIE_POSTER, movie.getPosterPath());
        values.put(MovieTable.COLUMN_MOVIE_RELEASE_DATE, movie.getReleaseDate());
        values.put(MovieTable.COLUMN_MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
        values.put(MovieTable.COLUMN_MOVIE_OVERVIEW, movie.getOverview());

        return values;
    }


    public static ContentValues[] toContentValuesArray(Movie[] movies) {

        if (movies == null || movies.length == 0)
            return null;

        int index = 0;
        ContentValues[] values = new ContentValues[movies.length];
        for (Movie movie : movies) {

            values[index] = new ContentValues();
            values[index].put(MovieTable.COLUMN_MOVIE_ID, movie.getId());
            values[index].put(MovieTable.COLUMN_MOVIE_TITLE, movie.getTitle());
            values[index].put(MovieTable.COLUMN_MOVIE_POSTER, movie.getPosterPath());
            values[index].put(MovieTable.COLUMN_MOVIE_RELEASE_DATE, movie.getReleaseDate());
            values[index].put(MovieTable.COLUMN_MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
            values[index].put(MovieTable.COLUMN_MOVIE_OVERVIEW, movie.getOverview());

            index++;
        }

        return values;
    }


    public static String whereParam(String column) {

        if (column == null)
            return null;

        return column + " = ?";
    }


    public static String[] whereArgs(Movie[] movies) {

        if (movies == null || movies.length == 0)
            return null;

        Movie movie = movies[0];
        return new String[]{String.valueOf(movie.getId())};
    }


    public static boolean isMovieSaved(Context context, int id) {
        return isMovieSavedInPopular(context, id) || isMovieSavedInTopRated(context, id);
    }


    private synchronized static boolean isMovieSaved(Context context, String tableName, int id) {

        Uri uri = null;

        switch (tableName) {

            case PopularMovieTable.TABLE_NAME:
                uri = PopularMovieTable.CONTENT_URI;
                break;

            case TopRatedMovieTable.TABLE_NAME:
                uri = TopRatedMovieTable.CONTENT_URI;
                break;
        }

        if (uri == null)
            return false;

        Cursor cursor = null;
        try {
            cursor = context.getContentResolver()
                    .query(uri,
                            null,
                            MovieTable.COLUMN_MOVIE_ID + " = ?",
                            new String[]{String.valueOf(id)},
                            null);

            if (cursor == null || cursor.getCount() == 0)
                return false;

            return true;
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }


    private static boolean isMovieSavedInPopular(Context context, int id) {
        return isMovieSaved(context, PopularMovieTable.TABLE_NAME, id);
    }


    private static boolean isMovieSavedInTopRated(Context context, int id) {
        return isMovieSaved(context, TopRatedMovieTable.TABLE_NAME, id);
    }
}
