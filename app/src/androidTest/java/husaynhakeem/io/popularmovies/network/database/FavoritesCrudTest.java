package husaynhakeem.io.popularmovies.network.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import husaynhakeem.io.popularmovies.database.FavoriteMovieTable;
import husaynhakeem.io.popularmovies.models.Movie;

import static husaynhakeem.io.popularmovies.network.database.DbTestUtils.deleteMovie;
import static husaynhakeem.io.popularmovies.network.database.DbTestUtils.insertMovie;
import static husaynhakeem.io.popularmovies.network.database.DbTestUtils.readMovie;

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
        int deletedRows = deleteMovie(FavoriteMovieTable.CONTENT_URI, movie.getId());
        Assert.assertTrue(deletedRows == 0 || deletedRows == 1);
    }


    @Test
    public void querying_favorites_should_return_empty_result() {
        givenMovieDeletedIfNeeded();
        Cursor cursor = readMovie(FavoriteMovieTable.CONTENT_URI, movie.getId());
        Assert.assertNotEquals(null, cursor);
        Assert.assertEquals(0, cursor.getCount());
    }


    @Test
    public void insert_favorite_movie() {
        Uri returnedUri = insertMovie(FavoriteMovieTable.CONTENT_URI, movie);
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
        Cursor cursor = readMovie(FavoriteMovieTable.CONTENT_URI, movie.getId());
        Assert.assertTrue(cursor != null);
        Assert.assertEquals(1, cursor.getCount());
    }


    private int givenMovieDeletedIfNeeded() {
        return deleteMovie(FavoriteMovieTable.CONTENT_URI, movie.getId());
    }


    private Uri givenMovieIsInserted() {
        return insertMovie(FavoriteMovieTable.CONTENT_URI, movie);
    }
}
