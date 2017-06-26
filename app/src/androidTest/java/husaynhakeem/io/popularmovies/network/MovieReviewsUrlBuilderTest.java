package husaynhakeem.io.popularmovies.network;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import junit.framework.Assert;

import org.junit.Test;

import husaynhakeem.io.popularmovies.R;

/**
 * Created by husaynhakeem on 6/26/17.
 */

public class MovieReviewsUrlBuilderTest {


    private static final String MOVIE_REVIEWS_URL = "https://api.themoviedb.org/3/movie/283995/reviews?api_key=d9cfa9b786b4f434116f6bc47d8ddcff";


    @Test
    public void built_movie_reviews_url_is_correct() {

        Context context = InstrumentationRegistry.getTargetContext();

        try {
            int apiKey = Integer.parseInt(context.getString(R.string.themoviedb_api_key));
            Assert.assertEquals(MOVIE_REVIEWS_URL, MovieReviewsNetworkUtils.buildReviewsUrl(context, apiKey));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
