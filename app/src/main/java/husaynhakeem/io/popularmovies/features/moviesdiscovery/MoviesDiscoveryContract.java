package husaynhakeem.io.popularmovies.features.moviesdiscovery;

import java.util.List;

import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.view.BaseView;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public interface MoviesDiscoveryContract extends BaseView {

    interface LoadMoreListener {
        void loadMore();
    }

    void bindMoviesToList(List<Movie> movies);

    void onMoviesListReset();

    void onLoading();

    void onDoneLoading();

    void displaySortCriteria(String sortCriteria);

    void onInternetConnection();

    void onNoInternetConnection();

    void setClickListener(MoviesAdapter.ClickListener listener);

    void setLoadMoreListener(LoadMoreListener listener);

    void scrollBackToTop();
}
