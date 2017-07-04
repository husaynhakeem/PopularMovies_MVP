package husaynhakeem.io.popularmovies.network;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by husaynhakeem on 6/26/17.
 */

public class MovieTrailersUrlBuilderTest {


    private static final String MOVIE_REVIEWS_URL = "https://api.themoviedb.org/3/movie/297762/videos?api_key=d9cfa9b786b4f434116f6bc47d8ddcff";
    private static final int MOVIE_ID = 297762;


    @Test
    public void should_build_correct_trailers_uri() {
        Context context = InstrumentationRegistry.getTargetContext();
        Assert.assertEquals(MOVIE_REVIEWS_URL, MovieTrailersNetworkUtils.buildTrailersUrl(context, MOVIE_ID).toString());
    }
}
