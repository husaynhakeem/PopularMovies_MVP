package husaynhakeem.io.popularmovies.network;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public class RequestsUrlBuildersTest {

    private static final String MOVIES_URL = "https://api.themoviedb.org/3/movie/popular?page=1&api_key=d9cfa9b786b4f434116f6bc47d8ddcff";
    private static final String POSTER_URL = "http://image.tmdb.org/t/p/w185/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";


    @Test
    public void buildingMoviesUrlIsCorrect() {

        Context context = InstrumentationRegistry.getTargetContext();
        assertEquals(MOVIES_URL, NetworkUtils.buildMoviesUrl(context, "popular", "1").toString());
    }


    @Test
    public void buildingMoviesPosterUrlIsCorrect() {

        Assert.assertEquals(POSTER_URL, NetworkUtils.buildPosterUrl("/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg").toString());
    }
}
