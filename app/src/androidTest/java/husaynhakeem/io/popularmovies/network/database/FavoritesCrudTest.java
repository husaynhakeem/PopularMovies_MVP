package husaynhakeem.io.popularmovies.network.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import husaynhakeem.io.popularmovies.database.FavoriteMovieTable;
import husaynhakeem.io.popularmovies.database.MovieTable;
import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.utilities.DbUtils;

/**
 * Created by husaynhakeem on 7/3/17.
 */

public class FavoritesCrudTest {

    private Context context = InstrumentationRegistry.getTargetContext();
    private Movie movie = new Movie(1,
            7.5,
            "The departed",
            "/some/path",
            "The movie is too complicated to explain shortly",
            "2006");


    @Before
    @Test
    public void delete_favorite_movie() {
        int deletedRows = deleteMovie();
        Assert.assertTrue(deletedRows == 0 || deletedRows == 1);
    }


    @Test
    public void querying_favorites_should_return_empty_result() {
        givenMovieDeletedIfNeeded();
        Cursor cursor = readMovie();
        Assert.assertNotEquals(null, cursor);
        Assert.assertEquals(0, cursor.getCount());
    }


    @Test
    public void insert_favorite_movie() {
        Uri returnedUri = insertMovie();
        try {
            long returnedRow = Long.parseLong(returnedUri.getPathSegments().get(1));
            Assert.assertTrue(returnedRow > 0);
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void querying_favorites_should_return_one_result() {
        givenMovieDeletedIfNeeded();
        givenMovieIsInserted();
        Cursor cursor = readMovie();
        Assert.assertTrue(cursor != null);
        Assert.assertEquals(1, cursor.getCount());
    }


    private Uri insertMovie() {
        return context.getContentResolver()
                .insert(FavoriteMovieTable.CONTENT_URI, DbUtils.toContentValues(new Movie[]{movie}));
    }


    private int deleteMovie() {
        return context.getContentResolver()
                .delete(FavoriteMovieTable.CONTENT_URI,
                        MovieTable.COLUMN_MOVIE_ID + " = ?",
                        new String[]{String.valueOf(movie.getId())}
                );
    }


    private Cursor readMovie() {
        return context.getContentResolver()
                .query(FavoriteMovieTable.CONTENT_URI,
                        null,
                        MovieTable.COLUMN_MOVIE_ID + " = ?",
                        new String[]{String.valueOf(movie.getId())},
                        null);
    }


    private void givenMovieDeletedIfNeeded() {
        deleteMovie();
    }


    private void givenMovieIsInserted() {
        insertMovie();
    }
}
