package husaynhakeem.io.popularmovies.features.moviesDiscovery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.models.Mapper;
import husaynhakeem.io.popularmovies.models.MoviesPage;
import husaynhakeem.io.popularmovies.network.NetworkUtils;

public class MoviesDiscoveryPresenter extends AppCompatActivity implements MoviesAdapter.ClickListener {


    private MoviesDiscoveryView discoveryView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        discoveryView = new MoviesDiscoveryView(getLayoutInflater(),
                (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content));
        setContentView(discoveryView.getRootView());

        loadMovies();
    }


    private void loadMovies() {
        if (!NetworkUtils.isInternetAvailable(this)) {
            discoveryView.onNoInternetConnection();
        } else {
            discoveryView.onInternetConnection();
            new MoviesTask().execute();
        }
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


    class MoviesTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            discoveryView.onLoading();
        }

        @Override
        protected String doInBackground(Void... params) {
            return NetworkUtils.getResponseFromUrl(NetworkUtils.buildMoviesUrl(MoviesDiscoveryPresenter.this, "popular", "1"));
        }

        @Override
        protected void onPostExecute(String s) {
            discoveryView.onDoneLoading();

            MoviesPage moviesPage = Mapper.convertFromJsonToMovies(s);
            discoveryView.bindMoviesToList(moviesPage.getMovies());
        }
    }
}
