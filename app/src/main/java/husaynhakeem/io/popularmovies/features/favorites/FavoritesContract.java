package husaynhakeem.io.popularmovies.features.favorites;

import android.database.Cursor;

import husaynhakeem.io.popularmovies.BasePresenter;
import husaynhakeem.io.popularmovies.BaseView;
import husaynhakeem.io.popularmovies.models.Movie;

/**
 * Created by husaynhakeem on 7/1/17.
 */

public interface FavoritesContract {


    interface View extends BaseView<Presenter> {

        void initViews();

        void bindMoviesToList(Cursor movies);

        void onLoading();

        void onDoneLoading();

        void onMovieClicked(Movie movie);

        void onNoFavorites();

        void onFavoritesChanged();
    }


    interface Presenter extends BasePresenter {

        void loadFavoriteMovies();

        void reloadFavoriteMovies();

        void onMovieClicked(Movie movie);
    }
}
