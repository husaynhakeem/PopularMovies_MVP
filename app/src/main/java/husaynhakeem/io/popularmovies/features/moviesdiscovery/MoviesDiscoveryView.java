package husaynhakeem.io.popularmovies.features.moviesdiscovery;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.view.EndlessRecyclerViewScrollListener;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public class MoviesDiscoveryView implements MoviesDiscoveryContract {

    private LoadMoreListener loadMoreListener;

    private View rootView;
    private RecyclerView moviesRecyclerView;
    private EndlessRecyclerViewScrollListener recyclerViewScrollListener;
    private MoviesAdapter moviesAdapter;
    private ProgressBar loadingProgressBar;
    private View noInternetLayout;


    public MoviesDiscoveryView(LayoutInflater layoutInflater, ViewGroup parent) {
        rootView = layoutInflater.inflate(R.layout.activity_movies_discovery, parent, false);
        initViews();
    }


    @Override
    public void initViews() {
        moviesRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(rootView.getContext(), 2);
        moviesRecyclerView.setLayoutManager(layoutManager);

        recyclerViewScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadMoreListener.loadMore();
            }
        };
        moviesRecyclerView.addOnScrollListener(recyclerViewScrollListener);

        moviesRecyclerView.setHasFixedSize(true);
        moviesAdapter = new MoviesAdapter(null, null);
        moviesRecyclerView.setAdapter(moviesAdapter);

        loadingProgressBar = (ProgressBar) rootView.findViewById(R.id.pb_loading);
        noInternetLayout = rootView.findViewById(R.id.layout_no_internet);
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


    @Override
    public void onLoading() {
        loadingProgressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onDoneLoading() {
        loadingProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void displaySortCriteria(String sortCriteria) {

        int messageSortCriteria;

        if (MoviesDiscoveryPresenter.SORT_BY_MOST_POPULAR.equals(sortCriteria))
            messageSortCriteria = R.string.message_sort_by_most_popular;
        else
            messageSortCriteria = R.string.message_sort_by_top_rated;

        Snackbar.make(rootView,
                messageSortCriteria,
                Snackbar.LENGTH_SHORT
        ).show();
    }


    @Override
    public void onInternetConnection() {
        moviesRecyclerView.setVisibility(View.VISIBLE);
        noInternetLayout.setVisibility(View.GONE);
    }


    @Override
    public void onNoInternetConnection() {
        moviesRecyclerView.setVisibility(View.GONE);
        loadingProgressBar.setVisibility(View.GONE);
        noInternetLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void setClickListener(MoviesAdapter.ClickListener listener) {
        moviesAdapter.setClickListener(listener);
    }


    @Override
    public void setLoadMoreListener(LoadMoreListener listener) {
        loadMoreListener = listener;
    }


    @Override
    public View getRootView() {
        return rootView;
    }


    @Override
    public Bundle getViewState() {
        return null;
    }
}
