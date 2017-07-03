package husaynhakeem.io.popularmovies.features.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import husaynhakeem.io.popularmovies.features.favorites.FavoritesPresenter;
import husaynhakeem.io.popularmovies.features.favorites.FavoritesView;
import husaynhakeem.io.popularmovies.features.movies.MoviesPresenter;
import husaynhakeem.io.popularmovies.features.movies.MoviesView;

/**
 * Created by husaynhakeem on 7/1/17.
 */

public class PagerAdapter extends FragmentPagerAdapter {


    private static final int NUMBER_OF_PAGES = 2;
    private static final int FIRST_PAGE = 0;
    private static final int SECOND_PAGE = 1;


    private MoviesView moviesView;
    private FavoritesView favoritesView;


    public PagerAdapter(FragmentManager fm) {
        super(fm);
        createPages();
    }


    private void createPages() {

        if (moviesView == null) {
            moviesView = new MoviesView();
            MoviesPresenter moviesPresenter = new MoviesPresenter();
            moviesView.setPresenter(moviesPresenter);
        }

        if (favoritesView == null) {
            favoritesView = new FavoritesView();
            FavoritesPresenter favoritesPresenter = new FavoritesPresenter();
            favoritesView.setPresenter(favoritesPresenter);

        }
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case FIRST_PAGE:
                return moviesView;

            case SECOND_PAGE:
                return favoritesView;

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
