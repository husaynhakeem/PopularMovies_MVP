package husaynhakeem.io.popularmovies.features.movieDetails;

import android.os.Bundle;

import husaynhakeem.io.popularmovies.view.BaseView;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public interface MovieDetailsContract extends BaseView {

    void setMoviePoster(String posterPath);

    void setMovieDetails(Bundle bundle);
}
