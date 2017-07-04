package husaynhakeem.io.popularmovies.features.details;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import husaynhakeem.io.popularmovies.database.DbTasks;
import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.network.MoviePosterNetworkUtils;
import husaynhakeem.io.popularmovies.utilities.DbUtils;

import static husaynhakeem.io.popularmovies.database.DbTasks.FAVORITE_DELETE;
import static husaynhakeem.io.popularmovies.database.DbTasks.FAVORITE_INSERT;
import static husaynhakeem.io.popularmovies.features.details.ReviewsLoader.MOVIE_REVIEWS_LOADER_ID;
import static husaynhakeem.io.popularmovies.features.details.TrailersLoader.MOVIE_TRAILERS_LOADER_ID;
import static husaynhakeem.io.popularmovies.models.Movie.MOVIE;

/**
 * Created by husaynhakeem on 7/1/17.
 */

public class DetailsPresenter implements DetailsContract.Presenter {

    public static final String MOVIE_ID = "movie_id";
    private DetailsView view;
    private Movie movie;


    @Override
    public void start() {
        populateView();
        checkIfMovieSaved();
        loadTrailers();
        loadReviews();
    }


    @Override
    public void populateView() {
        Bundle bundle = view.getArguments();

        if (bundle != null) {
            movie = bundle.getParcelable(MOVIE);
            view.setMovieGeneralInfo(movie);
            view.setMoviePoster(MoviePosterNetworkUtils.buildPosterUrl(movie.getPosterPath()).toString());
        }
    }


    @Override
    public void checkIfMovieSaved() {

        new AsyncTask<Void, Boolean, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... params) {
                return DbUtils.isMovieSaved(view.getContext(), movie.getId());
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                setUpFABImage(aBoolean);
                view.setMovieSaved(aBoolean);
            }
        }.execute();
    }


    @Override
    public void setUpFABImage(boolean isMovieSaved) {
        view.setFABImage(isMovieSaved);
    }


    @Override
    public void onSaveMovieClicked(boolean isMovieSaved) {

        if (isMovieSaved) {
            DbTasks.executeTask(view.getContext(),
                    FAVORITE_DELETE,
                    movie);
            view.onMovieUnsaved();
        } else {
            DbTasks.executeTask(view.getContext(),
                    FAVORITE_INSERT,
                    movie);
            view.onMovieSaved();
        }
    }


    @Override
    public void loadTrailers() {
        Bundle args = new Bundle();
        args.putInt(MOVIE_ID, movie.getId());
        TrailersLoader trailersLoader = new TrailersLoader(view);

        if (view.getActivity().getSupportLoaderManager().getLoader(MOVIE_TRAILERS_LOADER_ID) != null &&
                view.getActivity().getSupportLoaderManager().getLoader(MOVIE_TRAILERS_LOADER_ID).isStarted())
            view.getActivity().getSupportLoaderManager().restartLoader(MOVIE_TRAILERS_LOADER_ID, args, trailersLoader);
        else
            view.getActivity().getSupportLoaderManager().initLoader(MOVIE_TRAILERS_LOADER_ID, args, trailersLoader);
    }


    @Override
    public void loadReviews() {
        Bundle args = new Bundle();
        args.putInt(MOVIE_ID, movie.getId());
        ReviewsLoader reviewsLoader = new ReviewsLoader(view);

        if (view.getActivity().getSupportLoaderManager().getLoader(MOVIE_REVIEWS_LOADER_ID) != null &&
                view.getActivity().getSupportLoaderManager().getLoader(MOVIE_REVIEWS_LOADER_ID).isStarted())
            view.getActivity().getSupportLoaderManager().restartLoader(MOVIE_REVIEWS_LOADER_ID, args, reviewsLoader);
        else
            view.getActivity().getSupportLoaderManager().initLoader(MOVIE_REVIEWS_LOADER_ID, args, reviewsLoader);
    }


    @Override
    public void onRetry() {
        Toast.makeText(view.getContext(), "Retry!", Toast.LENGTH_SHORT).show();
    }


    public void setView(DetailsView view) {
        this.view = view;
    }
}
