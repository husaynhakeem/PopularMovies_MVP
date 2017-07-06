package husaynhakeem.io.popularmovies.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import husaynhakeem.io.popularmovies.models.Movie;

import static husaynhakeem.io.popularmovies.database.FavoriteMovieTable.COLUMN_MOVIE_ID;
import static husaynhakeem.io.popularmovies.database.FavoriteMovieTable.COLUMN_MOVIE_OVERVIEW;
import static husaynhakeem.io.popularmovies.database.FavoriteMovieTable.COLUMN_MOVIE_POSTER;
import static husaynhakeem.io.popularmovies.database.FavoriteMovieTable.COLUMN_MOVIE_RELEASE_DATE;
import static husaynhakeem.io.popularmovies.database.FavoriteMovieTable.COLUMN_MOVIE_TITLE;
import static husaynhakeem.io.popularmovies.database.FavoriteMovieTable.COLUMN_MOVIE_VOTE_AVERAGE;
import static husaynhakeem.io.popularmovies.database.FavoriteMovieTable.CONTENT_URI;

/**
 * Created by husaynhakeem on 6/27/17.
 */

public class DbUtils {


    public static ContentValues toContentValues(Movie[] movies) {

        if (movies == null || movies.length == 0)
            return null;

        Movie movie = movies[0];

        ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIE_ID, movie.getId());
        values.put(COLUMN_MOVIE_TITLE, movie.getTitle());
        values.put(COLUMN_MOVIE_POSTER, movie.getPosterPath());
        values.put(COLUMN_MOVIE_RELEASE_DATE, movie.getReleaseDate());
        values.put(COLUMN_MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
        values.put(COLUMN_MOVIE_OVERVIEW, movie.getOverview());

        return values;
    }


    public static ContentValues[] toContentValuesArray(Movie[] movies) {

        if (movies == null || movies.length == 0)
            return null;

        int index = 0;
        ContentValues[] values = new ContentValues[movies.length];
        for (Movie movie : movies) {

            values[index] = new ContentValues();
            values[index].put(COLUMN_MOVIE_ID, movie.getId());
            values[index].put(COLUMN_MOVIE_TITLE, movie.getTitle());
            values[index].put(COLUMN_MOVIE_POSTER, movie.getPosterPath());
            values[index].put(COLUMN_MOVIE_RELEASE_DATE, movie.getReleaseDate());
            values[index].put(COLUMN_MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
            values[index].put(COLUMN_MOVIE_OVERVIEW, movie.getOverview());

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
        Cursor cursor = context.getContentResolver()
                .query(CONTENT_URI,
                        null,
                        COLUMN_MOVIE_ID + " = ?",
                        new String[]{String.valueOf(id)},
                        null);

        return cursor != null && cursor.getCount() > 0;
    }
}
