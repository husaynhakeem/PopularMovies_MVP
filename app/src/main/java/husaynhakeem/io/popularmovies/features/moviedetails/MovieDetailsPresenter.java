package husaynhakeem.io.popularmovies.features.moviedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import static husaynhakeem.io.popularmovies.models.Movie.MOVIE_POSTER;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public class MovieDetailsPresenter extends AppCompatActivity {


    private MovieDetailsView movieDetailsView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieDetailsView = new MovieDetailsView(getLayoutInflater(), (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content));
        setContentView(movieDetailsView.getRootView());

        populateView();
    }


    private void populateView() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            movieDetailsView.setMovieDetails(bundle);

            if (bundle.containsKey(MOVIE_POSTER))
                movieDetailsView.setMoviePoster(bundle.getString(MOVIE_POSTER));
        }
    }
}
