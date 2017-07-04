package husaynhakeem.io.popularmovies.features.details;

import java.util.List;

import husaynhakeem.io.popularmovies.BasePresenter;
import husaynhakeem.io.popularmovies.BaseView;
import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.models.Review;
import husaynhakeem.io.popularmovies.models.Trailer;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public interface DetailsContract {

    interface View extends BaseView<Presenter> {

        void initViews();

        void setFABImage(boolean isMovieSaved);

        void setMoviePoster(String posterPath);

        void setMovieGeneralInfo(Movie movie);

        void setMovieTrailers(List<Trailer> trailers);

        void setMovieReviews(List<Review> reviews);

        /*
        Displays or hides the reviews loading indicator depending on
        the value of 'doneLoading'
        */
        void onReviewsLoading(boolean doneLoading);

        void onNoTrailers();

        void onNoReviews();

        /*
        - Hide the videos horizontal linear layout
        - Hide the reviews linear layout
        - Display the noInternet views
        */
        void onNoInternet();

        /*
        Opposite of onNoInternet()
         */
        void onInternet();

        void onSaveMovieClicked();

        void onMovieSaved();

        void onMovieUnsaved();

        /*
        Retry after internet connection gone.
        - Fetch for movie trailers
        - Fetch for movie reviews
         */
        void onRetry();

        void setMovieSaved(boolean isMovieSaved);
    }


    interface Presenter extends BasePresenter {

        void populateView();

        void checkIfMovieSaved();

        void setUpFABImage(boolean isMovieSaved);

        void onSaveMovieClicked(boolean isMovieSaved);

        void loadTrailers();

        void loadReviews();

        void onRetry();
    }
}
