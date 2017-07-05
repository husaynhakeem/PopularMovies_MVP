package husaynhakeem.io.popularmovies.features.favorites;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.database.DbTasks;
import husaynhakeem.io.popularmovies.features.details.DetailsPresenter;
import husaynhakeem.io.popularmovies.features.details.DetailsView;
import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.utilities.UiUtils;

import static husaynhakeem.io.popularmovies.database.DbTasks.FAVORITE_QUERY;
import static husaynhakeem.io.popularmovies.models.Movie.MOVIE;

/**
 * Created by husaynhakeem on 7/1/17.
 */

public class FavoritesPresenter implements FavoritesContract.Presenter, LoaderManager.LoaderCallbacks<Cursor> {


    private static final int FAVORITE_MOVIES_LOADER_ID = 2;
    private static FavoritesView view;


    @Override
    public void start() {
        loadFavoriteMovies();
    }


    @Override
    public void loadFavoriteMovies() {
        view.getActivity().getSupportLoaderManager().initLoader(FAVORITE_MOVIES_LOADER_ID, null, this);
    }


    @Override
    public void reloadFavoriteMovies() {
        view.getActivity().getSupportLoaderManager().restartLoader(FAVORITE_MOVIES_LOADER_ID, null, this);
    }


    @Override
    public void onMovieClicked(Movie movie) {
        DetailsView detailsView = new DetailsView();
        DetailsPresenter detailsPresenter = new DetailsPresenter();
        detailsView.setPresenter(detailsPresenter);
        detailsPresenter.setView(detailsView);

        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE, movie);
        detailsView.setArguments(bundle);

        int frameId = R.id.content_frame;
        if (UiUtils.isTablet())
            frameId = R.id.detail_frame;

        view.getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(frameId, detailsView)
                .addToBackStack("Details")
                .commit();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (id == FAVORITE_MOVIES_LOADER_ID)
            return new AsyncTaskLoader<Cursor>(view.getContext()) {

                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    view.onLoading();
                    forceLoad();
                }

                @Override
                public Cursor loadInBackground() {
                    return DbTasks.executeQuery(view.getContext(), FAVORITE_QUERY);
                }
            };

        return null;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        view.onDoneLoading();
        view.bindMoviesToList(data);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }


    public void setView(FavoritesView view) {
        this.view = view;
    }
}
