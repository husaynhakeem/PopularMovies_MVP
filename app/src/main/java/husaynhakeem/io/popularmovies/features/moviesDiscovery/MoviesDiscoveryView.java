package husaynhakeem.io.popularmovies.features.moviesDiscovery;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.models.Movie;

/**
 * Created by husaynhakeem on 6/11/17.
 */

public class MoviesDiscoveryView implements MoviesDiscoveryContract {

    private View rootView;
    private RecyclerView moviesRecyclerView;
    private MoviesAdapter moviesAdapter;
    private ProgressBar loadingProgressBar;
    private View noInternetLayout;


    public MoviesDiscoveryView(LayoutInflater layoutInflater, ViewGroup parent) {
        rootView = layoutInflater.inflate(R.layout.activity_movies_discovery, parent, false);
        initViews();
    }


    private void initViews() {
        moviesRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_movies);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(rootView.getContext(), 2));
        moviesRecyclerView.setHasFixedSize(true);
        moviesAdapter = new MoviesAdapter(null);
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
    public void onLoading() {
        loadingProgressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onDoneLoading() {
        loadingProgressBar.setVisibility(View.GONE);
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
    public View getRootView() {
        return rootView;
    }


    @Override
    public Bundle getViewState() {
        return null;
    }
}
