package husaynhakeem.io.popularmovies.features.details;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.widget.Toast;

import java.net.URL;
import java.util.List;

import husaynhakeem.io.popularmovies.models.Mapper;
import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.models.Review;
import husaynhakeem.io.popularmovies.models.ReviewsPage;
import husaynhakeem.io.popularmovies.network.GeneralNetworkUtils;
import husaynhakeem.io.popularmovies.network.MoviePosterNetworkUtils;
import husaynhakeem.io.popularmovies.network.MovieReviewsNetworkUtils;

import static husaynhakeem.io.popularmovies.models.Movie.MOVIE;

/**
 * Created by husaynhakeem on 7/1/17.
 */

public class DetailsPresenter implements DetailsContract.Presenter, LoaderManager.LoaderCallbacks<List<Review>> {

    private static final int MOVIE_REVIEWS_LOADER_ID = 1;
    private DetailsView view;
    private Movie movie;


    @Override
    public void start() {
        populateView();
        view.getActivity().getSupportLoaderManager().initLoader(MOVIE_REVIEWS_LOADER_ID, null, this);
    }


    @Override
    public void populateView() {
        Bundle bundle = view.getArguments();

        if (bundle != null) {
            movie = bundle.getParcelable(MOVIE);
            view.setMovieGeneralInfo(movie);
            view.setMoviePoster(MoviePosterNetworkUtils.buildPosterUrl(movie.getPosterPath()).toString());
        }
    }


    @Override
    public Loader<List<Review>> onCreateLoader(int id, Bundle args) {

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
                            URL movieReviewsUrl = MovieReviewsNetworkUtils.buildReviewsUrl(getContext(), movie.getId());
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


    @Override
    public void onSaveMovieClicked() {
        Toast.makeText(view.getContext(), "Save movie!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRetry() {
        Toast.makeText(view.getContext(), "Retry!", Toast.LENGTH_SHORT).show();
    }


    public void setView(DetailsView view) {
        this.view = view;
    }
}
