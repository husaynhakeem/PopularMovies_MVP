package husaynhakeem.io.popularmovies.features.movies;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import java.net.URL;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.features.MainActivity;
import husaynhakeem.io.popularmovies.features.details.DetailsPresenter;
import husaynhakeem.io.popularmovies.features.details.DetailsView;
import husaynhakeem.io.popularmovies.models.Mapper;
import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.models.MoviesPage;
import husaynhakeem.io.popularmovies.network.GeneralNetworkUtils;
import husaynhakeem.io.popularmovies.network.MoviesNetworkUtils;
import husaynhakeem.io.popularmovies.utilities.UiUtils;

import static husaynhakeem.io.popularmovies.models.Movie.MOVIE;

/**
 * Created by husaynhakeem on 7/1/17.
 */

public class MoviesPresenter implements MoviesContract.Presenter, LoaderManager.LoaderCallbacks<MoviesPage> {

    private static final int MOVIES_DISCOVERY_LOADER_ID = 0;

    public static final String SORT_BY_MOST_POPULAR = "popular";
    public static final String SORT_BY_TOP_RATED = "top_rated";
    private String sortCriteria = SORT_BY_MOST_POPULAR;

    private MoviesView view;

    private int currentPage = 1;
    private int totalPages = 1;


    @Override
    public void start() {
        loadMovies();
    }


    @Override
    public void loadMovies() {
        if (!GeneralNetworkUtils.isInternetAvailable(view.getContext())) {
            view.onNoInternetConnection();
        } else {
            view.onInternetConnection();

            if (canLoadMoreMovies()) {
                view.getActivity().getSupportLoaderManager().restartLoader(MOVIES_DISCOVERY_LOADER_ID, null, this);
            }
        }
    }


    @Override
    public boolean canLoadMoreMovies() {
        return currentPage <= totalPages;
    }


    @Override
    public void loadMore() {
        loadMovies();
    }


    @Override
    public void onSortingModeChanged() {

        currentPage = 1;
        totalPages = 1;
        switchSortCriteria();
    }


    @Override
    public void switchSortCriteria() {
        if (sortCriteria.equals(SORT_BY_MOST_POPULAR)) {
            sortCriteria = SORT_BY_TOP_RATED;
        } else {
            sortCriteria = SORT_BY_MOST_POPULAR;
        }
    }


    @Override
    public void displaySortCriteria() {
        view.displaySortCriteria(sortCriteria);
    }


    @Override
    public String getSortCriteria() {
        return sortCriteria;
    }


    @Override
    public void setSortCriteria(String sortCriteria) {
        this.sortCriteria = sortCriteria;
    }


    @Override
    public Loader<MoviesPage> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<MoviesPage>(view.getContext()) {

            private MoviesPage moviesPage;

            @Override
            protected void onStartLoading() {
                if (moviesPage != null) {
                    deliverResult(moviesPage);
                } else {
                    view.onLoading();
                    forceLoad();
                }
            }

            @Override
            public MoviesPage loadInBackground() {

                try {
                    URL moviesUrl = MoviesNetworkUtils.buildMoviesUrl(getContext(), sortCriteria, String.valueOf(currentPage));
                    String moviesPageJson = GeneralNetworkUtils.getResponseFromUrl(moviesUrl);
                    moviesPage = (MoviesPage) Mapper.instance().convertFromJsonToMovies(moviesPageJson, MoviesPage.class);

                    return moviesPage;

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }


    @Override
    public void onLoadFinished(Loader<MoviesPage> loader, MoviesPage data) {

        if (data == null)
            return;

        currentPage++;
        totalPages = data.getTotalPages();
        view.bindMoviesToList(data.getMovies());
        view.onDoneLoading();
    }


    @Override
    public void onLoaderReset(Loader<MoviesPage> loader) {
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
        if (UiUtils.isTablet()) {
            frameId = R.id.detail_frame;
            ((MainActivity) view.getActivity()).onMovieDetailDisplayed();
        }

        view.getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(frameId, detailsView)
                .addToBackStack("Details")
                .commit();
    }


    @Override
    public void onNoInternetConnection() {
        if (!GeneralNetworkUtils.isInternetAvailable(view.getContext()))
            view.onNoInternetConnection();
    }


    public void setView(MoviesView view) {
        this.view = view;
    }
}
