package husaynhakeem.io.popularmovies.features.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.utilities.UiUtils;
import husaynhakeem.io.popularmovies.view.EndlessRecyclerViewScrollListener;

/**
 * Created by husaynhakeem on 7/1/17.
 */

public class MoviesView extends Fragment implements MoviesContract.View {


    private static final String SORT_CRITERIA_KEY = "sort_criteria_key";


    private View rootView;
    private RecyclerView moviesRecyclerView;
    private EndlessRecyclerViewScrollListener recyclerViewScrollListener;
    private MoviesAdapter moviesAdapter;
    private ProgressBar loadingProgressBar;
    private View noInternetLayout;

    private MoviesPresenter presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presenter == null)
            setPresenter(new MoviesPresenter());
        presenter.setView(this);
        presenter.start();

        /*
        Adapter initialization moved here in order to keep only one instance of it
        through the fragment's view recreations.
        When the main fragment is replaced, this fragment stays in the created state,
        when the main fragment is returned to, this method is not re-run, the fragment's
        life-cycle starts from onCreateView() method
         */
        moviesAdapter = new MoviesAdapter(null, this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        initViews();
        reInitMovies();
        setHasOptionsMenu(true);
        return rootView;
    }


    private void reInitMovies() {
        if (moviesAdapter != null && moviesAdapter.getItemCount() > 0)
            moviesAdapter.reInit();
    }


    @Override
    public void initViews() {
        moviesRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), Math.max(2, UiUtils.numberOfMovieRows()));
        moviesRecyclerView.setLayoutManager(layoutManager);

        recyclerViewScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                presenter.loadMore();
            }
        };
        moviesRecyclerView.addOnScrollListener(recyclerViewScrollListener);
        moviesRecyclerView.setHasFixedSize(true);
        moviesRecyclerView.setAdapter(moviesAdapter);

        loadingProgressBar = (ProgressBar) rootView.findViewById(R.id.pb_loading);
        noInternetLayout = rootView.findViewById(R.id.layout_no_internet);

        rootView.findViewById(R.id.btn_reload_movies).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadMovies();
            }
        });

        presenter.checkForConnection();
    }


    @Override
    public void bindMoviesToList(List<Movie> movies) {
        moviesAdapter.addMovies(movies);
        moviesAdapter.notifyDataSetChanged();
    }


    @Override
    public void onMoviesListReset() {
        if (moviesAdapter.getMovies() != null)
            moviesAdapter.getMovies().clear();
        moviesAdapter.notifyDataSetChanged();
        recyclerViewScrollListener.resetState();
    }


    public void reloadMovies() {
        presenter.loadMovies();
    }


    @Override
    public void onLoading() {
        if (loadingProgressBar != null)
            loadingProgressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onDoneLoading() {
        if (loadingProgressBar != null)
            loadingProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void displaySortCriteria(String sortCriteria) {
        int messageSortCriteria;

        if (MoviesPresenter.SORT_BY_MOST_POPULAR.equals(sortCriteria))
            messageSortCriteria = R.string.message_sort_by_most_popular;
        else
            messageSortCriteria = R.string.message_sort_by_top_rated;

        Snackbar.make(rootView,
                messageSortCriteria,
                Snackbar.LENGTH_SHORT
        ).show();
    }


    @Override
    public void onNoInternetConnection() {
        if (moviesRecyclerView != null)
            moviesRecyclerView.setVisibility(View.GONE);

        if (loadingProgressBar != null)
            loadingProgressBar.setVisibility(View.GONE);

        if (noInternetLayout != null)
            noInternetLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void scrollBackToTop() {
        if (moviesRecyclerView != null)
            moviesRecyclerView.smoothScrollToPosition(0);
    }


    @Override
    public void onMovieClicked(Movie movie) {
        presenter.onMovieClicked(movie);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.action_sort:
                onMoviesListReset();
                presenter.onSortingModeChanged();
                presenter.loadMovies();
                presenter.displaySortCriteria();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SORT_CRITERIA_KEY, presenter.getSortCriteria());
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(SORT_CRITERIA_KEY))
            presenter.setSortCriteria(savedInstanceState.getString(SORT_CRITERIA_KEY));
    }


    @Override
    public void setPresenter(MoviesContract.Presenter presenter) {
        this.presenter = (MoviesPresenter) presenter;
    }
}
