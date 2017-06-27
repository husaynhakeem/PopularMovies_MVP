package husaynhakeem.io.popularmovies.features.moviesdiscovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.net.URL;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.features.moviedetails.MovieDetailsPresenter;
import husaynhakeem.io.popularmovies.models.Mapper;
import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.models.MoviesPage;
import husaynhakeem.io.popularmovies.network.GeneralNetworkUtils;
import husaynhakeem.io.popularmovies.network.MoviesNetworkUtils;

import static husaynhakeem.io.popularmovies.models.Movie.MOVIE;

public class MoviesDiscoveryPresenter extends AppCompatActivity implements MoviesAdapter.ClickListener, MoviesDiscoveryContract.LoadMoreListener, LoaderManager.LoaderCallbacks<MoviesPage> {

    private static final int MOVIES_DISCOVERY_LOADER_ID = 0;

    public static final String SORT_BY_MOST_POPULAR = "popular";
    public static final String SORT_BY_TOP_RATED = "top_rated";
    private static final String SORT_CRITERIA_KEY = "sort_criteria_key";
    private String sortCriteria = SORT_BY_MOST_POPULAR;

    private MoviesDiscoveryView discoveryView;

    private int currentPage = 1;
    private int totalPages = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        discoveryView = new MoviesDiscoveryView(getLayoutInflater(),
                (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content));
        setContentView(discoveryView.getRootView());

        discoveryView.setClickListener(this);
        discoveryView.setLoadMoreListener(this);

        loadMovies();
    }


    private boolean canLoadMoreMovies() {
        return currentPage <= totalPages;
    }


    private void loadMovies() {
        if (!GeneralNetworkUtils.isInternetAvailable(this)) {
            discoveryView.onNoInternetConnection();
        } else {
            discoveryView.onInternetConnection();

            if (canLoadMoreMovies()) {
                getSupportLoaderManager().restartLoader(MOVIES_DISCOVERY_LOADER_ID, null, this);
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
        Intent movieDetails = new Intent(this, MovieDetailsPresenter.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE, movie);
        movieDetails.putExtras(bundle);

        startActivity(movieDetails);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies_discovery, menu);
        return true;
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
    }


    private void displaySortCriteria() {
        discoveryView.displaySortCriteria(sortCriteria);
    }


    @Override
    public Loader<MoviesPage> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<MoviesPage>(this) {

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
                    URL moviesUrl = MoviesNetworkUtils.buildMoviesUrl(MoviesDiscoveryPresenter.this, sortCriteria, String.valueOf(currentPage));
                    String moviesPageJson = GeneralNetworkUtils.getResponseFromUrl(moviesUrl);
                    MoviesPage moviesPage = (MoviesPage) Mapper.instance().convertFromJsonToMovies(moviesPageJson, MoviesPage.class);

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
    }


    @Override
    public void onLoaderReset(Loader<MoviesPage> loader) {
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SORT_CRITERIA_KEY, sortCriteria);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(SORT_CRITERIA_KEY))
            sortCriteria = savedInstanceState.getString(SORT_CRITERIA_KEY);
    }
}
