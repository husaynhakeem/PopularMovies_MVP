package husaynhakeem.io.popularmovies.features.moviesDiscovery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.net.URL;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.models.Mapper;
import husaynhakeem.io.popularmovies.models.MoviesPage;
import husaynhakeem.io.popularmovies.network.NetworkUtils;

public class MoviesDiscoveryPresenter extends AppCompatActivity implements MoviesAdapter.ClickListener, MoviesDiscoveryContract.LoadMoreListener {


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
        if (!NetworkUtils.isInternetAvailable(this)) {
            discoveryView.onNoInternetConnection();
        } else {
            discoveryView.onInternetConnection();

            if (canLoadMoreMovies())
                new MoviesTask(currentPage, "top_rated").execute();
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
    public void onMovieClick(int id) {
        Toast.makeText(this, "Movie id: " + id, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "Sorting by most popular", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_sort_by_top_rated:
                Toast.makeText(this, "Sorting by top rated", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
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
