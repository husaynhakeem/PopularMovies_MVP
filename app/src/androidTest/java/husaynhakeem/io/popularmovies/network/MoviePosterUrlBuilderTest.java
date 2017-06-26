package husaynhakeem.io.popularmovies.network;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public class MoviePosterUrlBuilderTest {


    private static final String POSTER_URL = "http://image.tmdb.org/t/p/w185/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";


    @Test
    public void built_movie_poster_url_is_correct() {

        assertEquals(POSTER_URL, MoviePosterNetworkUtils.buildPosterUrl("/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg").toString());
    }
}
