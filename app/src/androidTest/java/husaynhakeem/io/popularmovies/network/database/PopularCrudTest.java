package husaynhakeem.io.popularmovies.network.database;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import husaynhakeem.io.popularmovies.database.FavoriteMovieTable;
import husaynhakeem.io.popularmovies.database.MovieTable;
import husaynhakeem.io.popularmovies.models.Movie;

/**
 * Created by husaynhakeem on 7/3/17.
 */

public class PopularCrudTest {

    private Context context = InstrumentationRegistry.getTargetContext();

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
        int deletedRows = givenMovieDeleted(movie1);
        Assert.assertTrue(deletedRows == 0 || deletedRows == 1);

        deletedRows = givenMovieDeleted(movie2);
        Assert.assertTrue(deletedRows == 0 || deletedRows == 1);
    }


    private int givenMovieDeleted(Movie movie) {
        return deleteMovie(movie.getId());
    }


    private int deleteMovie(int movieId) {
        return context.getContentResolver()
                .delete(FavoriteMovieTable.CONTENT_URI,
                        MovieTable.COLUMN_MOVIE_ID + " = ?",
                        new String[]{String.valueOf(movieId)}
                );
    }


    @Test
    public void querying_popular_should_return_empty_result() {

    }


    private int readMovie() {
        return -1;
    }
}
