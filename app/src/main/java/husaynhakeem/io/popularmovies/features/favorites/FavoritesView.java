package husaynhakeem.io.popularmovies.features.favorites;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import husaynhakeem.io.popularmovies.R;
import husaynhakeem.io.popularmovies.models.Movie;
import husaynhakeem.io.popularmovies.utilities.UiUtils;

/**
 * Created by husaynhakeem on 7/1/17.
 */

public class FavoritesView extends Fragment implements FavoritesContract.View {

    private View rootView;
    private RecyclerView favoritesRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private FavoritesAdapter adapter;
    private TextView noFavoritesTextView;
    private ProgressBar loadingProgressBar;
    private FavoritesPresenter presenter;

    // Variables useful for screen rotations
    private static Cursor favoriteMovies;
    private static int favoriteMoviesCurrentIndex = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presenter == null)
            setPresenter(new FavoritesPresenter());
        presenter.setView(this);

        if (savedInstanceState == null)
            presenter.start();

        adapter = new FavoritesAdapter(favoriteMovies, this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        initViews();
        return rootView;
    }


    @Override
    public void initViews() {
        favoritesRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_favorites);
        gridLayoutManager = new GridLayoutManager(getContext(), Math.max(2, UiUtils.numberOfMovieRows()));
        favoritesRecyclerView.setLayoutManager(gridLayoutManager);
        favoritesRecyclerView.setHasFixedSize(true);

        favoritesRecyclerView.setAdapter(adapter);
        Log.e("Favorites", "Position" + favoriteMoviesCurrentIndex);
        favoritesRecyclerView.smoothScrollToPosition(favoriteMoviesCurrentIndex);

        noFavoritesTextView = (TextView) rootView.findViewById(R.id.tv_no_favorites);
        loadingProgressBar = (ProgressBar) rootView.findViewById(R.id.pb_loading);

        if (adapter.getItemCount() == 0)
            onNoFavorites();
    }


    @Override
    public void bindMoviesToList(Cursor movies) {
        if (adapter != null) {
            adapter.putFavorites(movies);
            favoriteMovies = movies;
            if (movies == null || movies.getCount() == 0) {
                onNoFavorites();
            }
        }
    }


    @Override
    public void onLoading() {
        if (loadingProgressBar != null)
            loadingProgressBar.setVisibility(View.VISIBLE);

        if (favoritesRecyclerView != null)
            favoritesRecyclerView.setVisibility(View.VISIBLE);

        if (noFavoritesTextView != null)
            noFavoritesTextView.setVisibility(View.GONE);
    }


    @Override
    public void onDoneLoading() {
        if (loadingProgressBar != null)
            loadingProgressBar.setVisibility(View.GONE);

        if (favoritesRecyclerView != null)
            favoritesRecyclerView.setVisibility(View.VISIBLE);

        if (noFavoritesTextView != null)
            noFavoritesTextView.setVisibility(View.GONE);
    }


    @Override
    public void onMovieClicked(Movie movie) {
        presenter.onMovieClicked(movie);
    }


    @Override
    public void onNoFavorites() {
        loadingProgressBar.setVisibility(View.GONE);
        favoritesRecyclerView.setVisibility(View.GONE);
        noFavoritesTextView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onFavoritesChanged() {
        presenter.reloadFavoriteMovies();
    }


    @Override
    public void setPresenter(FavoritesContract.Presenter presenter) {
        this.presenter = (FavoritesPresenter) presenter;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() >= 0)
            favoriteMoviesCurrentIndex = gridLayoutManager.findLastCompletelyVisibleItemPosition();
    }
}
