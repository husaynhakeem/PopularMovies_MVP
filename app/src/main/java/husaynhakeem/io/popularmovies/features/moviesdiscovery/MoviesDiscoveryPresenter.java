package husaynhakeem.io.popularmovies.features.moviesdiscovery;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.net.URL;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.features.moviedetails.MovieDetailsPresenter;
import husaynhakeem.io.popularmovies.models.Mapper;
import husaynhakeem.io.popularmovies.models.MoviesPage;
import husaynhakeem.io.popularmovies.network.NetworkUtils;

public class MoviesDiscoveryPresenter extends AppCompatActivity implements MoviesAdapter.ClickListener, MoviesDiscoveryContract.LoadMoreListener {

    private static final String SORT_BY_MOST_POPULAR = "popular";
    private static final String SORT_BY_TOP_RATED = "top_rated";

    private MoviesDiscoveryView discoveryView;

    private int currentPage = 1;
    private int totalPages = 1;
    private String currentSortingMode = SORT_BY_MOST_POPULAR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        discoveryView = new MoviesDiscoveryView(getLayoutInflater(),
                (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content));
        setContentView(discoveryView.getRootView());

        discoveryView.setClickListener(this);
        discoveryView.setLoadMoreListener(this);

        loadMovies(currentSortingMode);
    }


    private boolean canLoadMoreMovies() {
        return currentPage <= totalPages;
    }


    private void loadMovies(String sortingMode) {
        if (!NetworkUtils.isInternetAvailable(this)) {
            discoveryView.onNoInternetConnection();
        } else {
            discoveryView.onInternetConnection();

            if (canLoadMoreMovies())
                new MoviesTask(currentPage, sortingMode).execute();
        }
    }


    @Override
    public void loadMore() {
        loadMovies(currentSortingMode);
    }


    public void reloadMovies(View view) {
        loadMovies(currentSortingMode);
    }


    @Override
    public void onMovieClick(Bundle bundle) {
        Intent movieDetails = new Intent(this, MovieDetailsPresenter.class);
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

            case R.id.action_sort_by_popular:
                discoveryView.onMoviesListReset();
                onSortingModeChanged();
                loadMovies(SORT_BY_MOST_POPULAR);
                return true;

            case R.id.action_sort_by_top_rated:
                discoveryView.onMoviesListReset();
                onSortingModeChanged();
                loadMovies(SORT_BY_TOP_RATED);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void onSortingModeChanged() {

        currentPage = 1;
        totalPages = 1;
    }


    private class MoviesTask extends AsyncTask<Void, Void, String> {


        private int page;
        private String sortOption;


        public MoviesTask(int page, String sortOption) {
            this.page = page;
            this.sortOption = sortOption;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            discoveryView.onLoading();
        }


        @Override
        protected String doInBackground(Void... params) {
            URL moviesUrl = NetworkUtils.buildMoviesUrl(MoviesDiscoveryPresenter.this, sortOption, String.valueOf(page));
            return NetworkUtils.getResponseFromUrl(moviesUrl);
        }


        @Override
        protected void onPostExecute(String s) {
            MoviesPage moviesPage = Mapper.convertFromJsonToMovies(s);
            onPostLoadingMovies(moviesPage);

            discoveryView.onDoneLoading();
        }


        private void onPostLoadingMovies(MoviesPage moviesPage) {
            currentPage++;
            totalPages = moviesPage.getTotalPages();
            discoveryView.bindMoviesToList(moviesPage.getMovies());
        }
    }
}
