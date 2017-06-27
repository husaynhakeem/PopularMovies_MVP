package husaynhakeem.io.popularmovies.features.moviedetails;

import java.util.List;

import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.models.Review;
import husaynhakeem.io.popularmovies.view.BaseView;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public interface MovieDetailsContract extends BaseView {


    void setMoviePoster(String posterPath);


    void setMovieGeneralInfo(Movie movie);


    void setMovieReviews(List<Review> reviews);


    /*
    Displays or hides the reviews loading indicator depending on
    the value of 'doneLoading'
     */
    void onReviewsLoading(boolean doneLoading);


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


    interface ClickHandler {

        /*
        Saves the current movie in the database
        The bundle contains all the data to be saved
         */
        void onSaveMovieClicked();


        /*
        Shares the trailer of the current movie
        The bundle contains the movie name and trailer URL
         */
        void onShareMovie();


        /*
        Retry after internet connection gone.
        - Fetch for movie trailers
        - Fetch for movie reviews
         */
        void onRetry();
    }


    void setClickHandler(ClickHandler clickHandler);
}
