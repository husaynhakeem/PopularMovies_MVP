package husaynhakeem.io.popularmovies.features.moviesdiscovery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.net.URL;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.database.DbTasks;
import husaynhakeem.io.popularmovies.features.moviedetails.MovieDetailsPresenter;
import husaynhakeem.io.popularmovies.models.Mapper;
import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.models.MoviesPage;
import husaynhakeem.io.popularmovies.network.GeneralNetworkUtils;
import husaynhakeem.io.popularmovies.network.MoviesNetworkUtils;

import static husaynhakeem.io.popularmovies.database.DbTasks.POPULAR_BULK_DELETE;
import static husaynhakeem.io.popularmovies.database.DbTasks.POPULAR_BULK_INSERT;
import static husaynhakeem.io.popularmovies.database.DbTasks.TOP_RATED_BULK_DELETE;
import static husaynhakeem.io.popularmovies.database.DbTasks.TOP_RATED_BULK_INSERT;
import static husaynhakeem.io.popularmovies.models.Movie.MOVIE;

public class MoviesDiscoveryPresenter extends Fragment implements MoviesAdapter.ClickListener, MoviesDiscoveryContract.LoadMoreListener, LoaderManager.LoaderCallbacks<MoviesPage> {

    private static final int MOVIES_DISCOVERY_LOADER_ID = 0;

    public static final String SORT_BY_MOST_POPULAR = "popular";
    public static final String SORT_BY_TOP_RATED = "top_rated";
    private static final String SORT_CRITERIA_KEY = "sort_criteria_key";
    private String sortCriteria = SORT_BY_MOST_POPULAR;

    private MoviesDiscoveryView discoveryView;

    private int currentPage = 1;
    private int totalPages = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return discoveryView.getRootView();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        discoveryView = new MoviesDiscoveryView(getActivity().getLayoutInflater(), (ViewGroup) getActivity().findViewById(android.R.id.content));

        discoveryView.setClickListener(this);
        discoveryView.setLoadMoreListener(this);

        loadMovies();

        setHasOptionsMenu(true);
    }


    private boolean canLoadMoreMovies() {
        return currentPage <= totalPages;
    }


    private void loadMovies() {
        if (!GeneralNetworkUtils.isInternetAvailable(getContext())) {
            discoveryView.onNoInternetConnection();
        } else {
            discoveryView.onInternetConnection();

            if (canLoadMoreMovies()) {
                getActivity().getSupportLoaderManager().restartLoader(MOVIES_DISCOVERY_LOADER_ID, null, this);
            }
        }
    }


    @Override
    public void loadMore() {
        loadMovies();
    }


    public void reloadMovies(View view) {
        loadMovies();
    }


    @Override
    public void onMovieClick(Movie movie) {

        MovieDetailsPresenter movieDetails = new MovieDetailsPresenter();

        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE, movie);
        movieDetails.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_content, movieDetails)
                .addToBackStack("Details")
                .commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.action_sort:
                discoveryView.onMoviesListReset();
                onSortingModeChanged();
                loadMovies();
                displaySortCriteria();
                return true;

            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void onSortingModeChanged() {

        currentPage = 1;
        totalPages = 1;
        switchSortCriteria();
    }


    private void switchSortCriteria() {
        if (sortCriteria.equals(SORT_BY_MOST_POPULAR)) {
            sortCriteria = SORT_BY_TOP_RATED;
        } else {
            sortCriteria = SORT_BY_MOST_POPULAR;
        }

        DbTasks.executeTask(getContext(),
                sortCriteria.equals(SORT_BY_MOST_POPULAR) ? POPULAR_BULK_DELETE : TOP_RATED_BULK_DELETE);
    }


    private void displaySortCriteria() {
        discoveryView.displaySortCriteria(sortCriteria);
    }


    @Override
    public Loader<MoviesPage> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<MoviesPage>(getContext()) {

            private MoviesPage moviesPage;

            @Override
            protected void onStartLoading() {
                if (moviesPage != null) {
                    deliverResult(moviesPage);
                } else {
                    discoveryView.onLoading();
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
        currentPage++;
        totalPages = data.getTotalPages();
        discoveryView.bindMoviesToList(data.getMovies());
        discoveryView.onDoneLoading();

        DbTasks.executeTask(getContext(),
                sortCriteria.equals(SORT_BY_MOST_POPULAR) ? POPULAR_BULK_INSERT : TOP_RATED_BULK_INSERT,
                data.getMovies().toArray(new Movie[data.getMovies().size()]));
    }


    @Override
    public void onLoaderReset(Loader<MoviesPage> loader) {
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SORT_CRITERIA_KEY, sortCriteria);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(SORT_CRITERIA_KEY))
            sortCriteria = savedInstanceState.getString(SORT_CRITERIA_KEY);
    }
}
