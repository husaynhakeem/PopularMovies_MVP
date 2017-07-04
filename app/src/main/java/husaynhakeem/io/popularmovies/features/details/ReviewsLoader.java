package husaynhakeem.io.popularmovies.features.details;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import java.net.URL;
import java.util.List;

import husaynhakeem.io.popularmovies.models.Mapper;
import husaynhakeem.io.popularmovies.models.Review;
import husaynhakeem.io.popularmovies.models.ReviewsPage;
import husaynhakeem.io.popularmovies.network.GeneralNetworkUtils;
import husaynhakeem.io.popularmovies.network.MovieReviewsNetworkUtils;

import static husaynhakeem.io.popularmovies.features.details.DetailsPresenter.MOVIE_ID;

/**
 * Created by husaynhakeem on 7/4/17.
 */

public class ReviewsLoader implements LoaderManager.LoaderCallbacks<List<Review>> {


    public static final int MOVIE_REVIEWS_LOADER_ID = 1;
    private DetailsView view;


    public ReviewsLoader(DetailsView view) {
        this.view = view;
    }


    @Override
    public Loader<List<Review>> onCreateLoader(int id, final Bundle args) {

        switch (id) {

            case MOVIE_REVIEWS_LOADER_ID:
                return new AsyncTaskLoader<List<Review>>(view.getContext()) {

                    private List<Review> movieReviews;


                    @Override
                    protected void onStartLoading() {
                        if (movieReviews != null) {
                            deliverResult(movieReviews);
                        } else {
                            view.onReviewsLoading(true);
                            forceLoad();
                        }
                    }

                    @Override
                    public List<Review> loadInBackground() {
                        try {
                            URL movieReviewsUrl = MovieReviewsNetworkUtils.buildReviewsUrl(getContext(), args.getInt(MOVIE_ID));
                            String movieReviewsJson = GeneralNetworkUtils.getResponseFromUrl(movieReviewsUrl);
                            ReviewsPage reviewsPage = (ReviewsPage) Mapper.instance().convertFromJsonToMovies(movieReviewsJson, ReviewsPage.class);
                            movieReviews = reviewsPage.getReviews();
                            return movieReviews;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                };

            default:
                return null;
        }
    }


    @Override
    public void onLoadFinished(Loader<List<Review>> loader, List<Review> data) {

        int id = loader.getId();

        switch (id) {
            case MOVIE_REVIEWS_LOADER_ID:
                view.setMovieReviews(data);
                view.onReviewsLoading(false);
                break;

            default:
                break;
        }
    }


    @Override
    public void onLoaderReset(Loader<List<Review>> loader) {
    }
}
