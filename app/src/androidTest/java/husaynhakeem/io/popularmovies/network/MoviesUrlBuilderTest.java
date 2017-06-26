package husaynhakeem.io.popularmovies.network;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by husaynhakeem on 6/26/17.
 */

public class MoviesUrlBuilderTest {


    private static final String MOVIES_URL = "https://api.themoviedb.org/3/movie/popular?page=1&api_key=d9cfa9b786b4f434116f6bc47d8ddcff";


    @Test
    public void built_movies_url_is_correct() {

        Context context = InstrumentationRegistry.getTargetContext();
        Assert.assertEquals(MOVIES_URL, MoviesNetworkUtils.buildMoviesUrl(context, "popular", "1").toString());
    }
}
