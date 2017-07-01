package husaynhakeem.io.popularmovies.features;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import husaynhakeem.io.popularmovies.features.favoritesdiscovery.FavoritesDiscoveryPresenter;
import husaynhakeem.io.popularmovies.features.moviesdiscovery.MoviesDiscoveryPresenter;

/**
 * Created by husaynhakeem on 7/1/17.
 */

public class PagerAdapter extends FragmentPagerAdapter {


    private static final int NUMBER_OF_PAGES = 2;
    private static final int FIRST_PAGE = 0;
    private static final int SECOND_PAGE = 1;


    private MoviesDiscoveryPresenter moviesPresenter;
    private FavoritesDiscoveryPresenter favoritesPresenter;


    public PagerAdapter(FragmentManager fm) {
        super(fm);
        createPages();
    }


    private void createPages() {
        moviesPresenter = new MoviesDiscoveryPresenter();
        favoritesPresenter = new FavoritesDiscoveryPresenter();
    }


    @Override
    public Fragment getItem(int position) {

        switch (position ) {

            case FIRST_PAGE:
                return moviesPresenter;

            case SECOND_PAGE:
                return favoritesPresenter;

            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return NUMBER_OF_PAGES;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
