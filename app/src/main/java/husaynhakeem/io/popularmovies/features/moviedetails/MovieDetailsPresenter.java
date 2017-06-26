package husaynhakeem.io.popularmovies.features.moviedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import java.net.URL;
import java.util.List;

import husaynhakeem.io.popularmovies.models.Mapper;
import husaynhakeem.io.popularmovies.models.Review;
import husaynhakeem.io.popularmovies.models.ReviewsPage;
import husaynhakeem.io.popularmovies.network.GeneralNetworkUtils;
import husaynhakeem.io.popularmovies.network.MovieReviewsNetworkUtils;

import static husaynhakeem.io.popularmovies.models.Movie.MOVIE_ID;
import static husaynhakeem.io.popularmovies.models.Movie.MOVIE_POSTER;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public class MovieDetailsPresenter extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Review>> {


    private static final int MOVIE_REVIEWS_LOADER_ID = 1;

    private MovieDetailsView movieDetailsView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieDetailsView = new MovieDetailsView(getLayoutInflater(), (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content));
        setContentView(movieDetailsView.getRootView());

        populateView();
        getSupportLoaderManager().initLoader(MOVIE_REVIEWS_LOADER_ID, null, this);
    }


    private void populateView() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            movieDetailsView.setMovieDetails(bundle);

            if (bundle.containsKey(MOVIE_POSTER))
                movieDetailsView.setMoviePoster(bundle.getString(MOVIE_POSTER));
        }
    }


    @Override
    public Loader<List<Review>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Review>>(this) {

            private List<Review> movieReviews;


            @Override
            protected void onStartLoading() {
                if (movieReviews != null) {
                    deliverResult(movieReviews);
                } else {
                    movieDetailsView.showLoadingIndicator(true);
                    forceLoad();
                }
            }

            @Override
            public List<Review> loadInBackground() {
                try {
                    int movieId = getIntent().getExtras().getInt(MOVIE_ID);
                    URL movieReviewsUrl = MovieReviewsNetworkUtils.buildReviewsUrl(MovieDetailsPresenter.this, movieId);
                    String movieReviews = GeneralNetworkUtils.getResponseFromUrl(movieReviewsUrl);
                    ReviewsPage reviewsPage = (ReviewsPage) Mapper.instance().convertFromJsonToMovies(movieReviews, ReviewsPage.class);
                    return reviewsPage.getReviews();
                } catch(Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }


    @Override
    public void onLoadFinished(Loader<List<Review>> loader, List<Review> data) {
        movieDetailsView.setMovieReviews(data);
        movieDetailsView.showLoadingIndicator(false);
    }


    @Override
    public void onLoaderReset(Loader<List<Review>> loader) {
    }
}
