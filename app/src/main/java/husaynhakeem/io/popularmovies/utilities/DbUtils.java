package husaynhakeem.io.popularmovies.utilities;

import android.content.ContentValues;

import husaynhakeem.io.popularmovies.database.MovieTable;
import husaynhakeem.io.popularmovies.models.Movie;

/**
 * Created by husaynhakeem on 6/27/17.
 */

public class DbUtils {


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

    public static String[] whereArgs(Movie[] movies) {

        if (movies == null || movies.length == 0)
            return null;

        Movie movie = movies[0];
        return new String[]{String.valueOf(movie.getId())};
    }
}
