package husaynhakeem.io.popularmovies.network.database;

import org.junit.Before;

import husaynhakeem.io.popularmovies.models.Movie;

/**
 * Created by husaynhakeem on 7/3/17.
 */

public class PopularCrudTest {


    private Movie movie1;
    private Movie movie2;


    @Before
    public void create_dummy_movies() {
        movie1 = new Movie(2,
                8.2,
                "Zootopia",
                "/first/movie/path",
                "Children -or family- animated movie",
                "2015");

        movie2 = new Movie(3,
                8.7,
                "Wonder woman",
                "/second/movie/path",
                "DC or Marvel superhero movie",
                "2017");
    }



}
