package husaynhakeem.io.popularmovies.features.moviedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
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
 * Created by husaynhakeem on 6/11/17.
 */

public class MovieDetailsPresenter extends AppCompatActivity implements MovieDetailsContract.ClickHandler, LoaderManager.LoaderCallbacks<List<Review>> {


    private static final int MOVIE_REVIEWS_LOADER_ID = 1;

    private MovieDetailsView movieDetailsView;
    private Movie movie;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieDetailsView = new MovieDetailsView(getLayoutInflater(), (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content));
        movieDetailsView.setClickHandler(this);
        setContentView(movieDetailsView.getRootView());

        populateView();
        getSupportLoaderManager().initLoader(MOVIE_REVIEWS_LOADER_ID, null, this);
    }


    private void populateView() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            movie = bundle.getParcelable(MOVIE);
            movieDetailsView.setMovieGeneralInfo(movie);

            movie.setPosterPath(MoviePosterNetworkUtils.buildPosterUrl(movie.getPosterPath()).toString());
            movieDetailsView.setMoviePoster(movie.getPosterPath());
        }
    }


    @Override
    public Loader<List<Review>> onCreateLoader(int id, Bundle args) {

        switch (id) {
            case MOVIE_REVIEWS_LOADER_ID:
                return new AsyncTaskLoader<List<Review>>(this) {

                    private List<Review> movieReviews;


                    @Override
                    protected void onStartLoading() {
                        if (movieReviews != null) {
                            deliverResult(movieReviews);
                        } else {
                            movieDetailsView.onReviewsLoading(true);
                            forceLoad();
                        }
                    }

                    @Override
                    public List<Review> loadInBackground() {
                        try {
                            URL movieReviewsUrl = MovieReviewsNetworkUtils.buildReviewsUrl(MovieDetailsPresenter.this, movie.getId());
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
                movieDetailsView.setMovieReviews(data);
                movieDetailsView.onReviewsLoading(false);
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
        Toast.makeText(this, "Save movie!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShareMovie() {
        Toast.makeText(this, "Share movie!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRetry() {
        Toast.makeText(this, "Retry !", Toast.LENGTH_SHORT).show();
    }
}
