package husaynhakeem.io.popularmovies.features.moviesDiscovery;

import java.util.List;

import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.view.BaseView;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public interface MoviesDiscoveryContract extends BaseView {

    void bindMoviesToList(List<Movie> movies);

    void onLoading();

    void onDoneLoading();

    void onInternetConnection();

    void onNoInternetConnection();
}
