package husaynhakeem.io.popularmovies.network.database;

import android.database.Cursor;
import android.net.Uri;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import husaynhakeem.io.popularmovies.database.PopularMovieTable;
import husaynhakeem.io.popularmovies.models.Movie;

import static husaynhakeem.io.popularmovies.network.database.DbTestUtils.bulkInsertMovies;
import static husaynhakeem.io.popularmovies.network.database.DbTestUtils.deleteMovie;
import static husaynhakeem.io.popularmovies.network.database.DbTestUtils.insertMovie;
import static husaynhakeem.io.popularmovies.network.database.DbTestUtils.readMovie;

/**
 * Created by husaynhakeem on 7/3/17.
 */

public class PopularCrudTest {

    private Movie movie1 = new Movie(2,
            8.2,
            "Zootopia",
            "/first/movie/path",
            "Children -or family- animated movie",
            "2015");

    private Movie movie2 = new Movie(3,
            8.7,
            "Wonder woman",
            "/second/movie/path",
            "DC or Marvel superhero movie",
            "2017");


    @Before
    @Test
    public void make_sure_popular_movies_deleted() {
        int deletedRows = givenAllMoviesDeleted();
        Assert.assertTrue(deletedRows == 0 || deletedRows == 1 || deletedRows == 2);
    }


    @Test
    public void querying_popular_should_return_empty_result() {
        givenAllMoviesDeleted();
        Cursor cursor = readMovie(PopularMovieTable.CONTENT_URI);
        Assert.assertTrue(cursor != null);
        Assert.assertEquals(0, cursor.getCount());
    }


    @Test
    public void insert_popular_movie() {
        Uri returnedUri = insertMovie(PopularMovieTable.CONTENT_URI, movie1);
        try {
            long returnedRow = Long.parseLong(returnedUri.getPathSegments().get(1));
            Assert.assertTrue(returnedRow > 0);
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void querying_popular_should_return_one_result() {
        givenAllMoviesDeleted();
        givenMovieInserted(movie1);
        Cursor cursor = readMovie(PopularMovieTable.CONTENT_URI, movie1.getId());
        Assert.assertTrue(cursor != null);
        Assert.assertEquals(1, cursor.getCount());
    }


    @Test
    public void bulk_insert_popular_movies() {
        givenAllMoviesDeleted();
        int insertedRows = bulkInsertMovies(PopularMovieTable.CONTENT_URI, new Movie[]{movie1, movie2});
        Assert.assertEquals(2, insertedRows);
    }


    @Test
    public void querying_popular_should_return_two_results() {
        givenAllMoviesDeleted();
        givenAllMoviesInserted();
        Cursor cursor = readMovie(PopularMovieTable.CONTENT_URI);
        Assert.assertTrue(cursor != null);
        Assert.assertEquals(2, cursor.getCount());
    }


    private int givenAllMoviesDeleted() {
        int deletedRows = 0;
        deletedRows += givenMovieDeleted(movie1);
        deletedRows += givenMovieDeleted(movie2);
        return deletedRows;
    }


    private int givenMovieDeleted(Movie movie) {
        return deleteMovie(PopularMovieTable.CONTENT_URI, movie.getId());
    }


    private void givenAllMoviesInserted() {
        givenMovieInserted(movie1);
        givenMovieInserted(movie2);
    }


    private void givenMovieInserted(Movie movie) {
        insertMovie(PopularMovieTable.CONTENT_URI, movie);
    }
}
