package husaynhakeem.io.popularmovies.features.movies;

import husaynhakeem.io.popularmovies.BasePresenter;
import husaynhakeem.io.popularmovies.BaseView;
import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.models.MoviesPage;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public interface MoviesContract extends BaseView {

    interface View extends BaseView<Presenter> {

        void initViews();

        void bindMoviesToList(MoviesPage moviesPage);

        void onMoviesListReset();

        void onLoading();

        void onDoneLoading();

        void displaySortCriteria(String sortCriteria);

        void onNoInternetConnection();

        void scrollBackToTop();

        void onMovieClicked(Movie movie);
    }


    interface Presenter extends BasePresenter {

        boolean canLoadMoreMovies();

        void loadMovies();

        void loadMore();

        void onSortingModeChanged();

        void switchSortCriteria();

        void displaySortCriteria();

        String getSortCriteria();

        void setSortCriteria(String sortCriteria);

        void onMovieClicked(Movie movie);

        void checkForConnection();

        void onNoInternetConnection();
    }
}
