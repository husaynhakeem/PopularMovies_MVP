package husaynhakeem.io.popularmovies.features.main;

import husaynhakeem.io.popularmovies.features.favorites.FavoritesView;

/**
 * Created by husaynhakeem on 7/1/17.
 */

public class DiscoveryPresenter implements DiscoveryContract.Presenter {

    private DiscoveryView view;


    @Override
    public void start() {
    }


    @Override
    public void onFavoritesChanged() {
        FavoritesView favoritesView = (FavoritesView) view.getPagerAdapter().getItem(1);
        favoritesView.onFavoritesChanged();
    }


    public void setView(DiscoveryView view) {
        this.view = view;
    }
}
