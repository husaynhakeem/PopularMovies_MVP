package husaynhakeem.io.popularmovies.features.discovery;

import husaynhakeem.io.popularmovies.BasePresenter;
import husaynhakeem.io.popularmovies.BaseView;

/**
 * Created by husaynhakeem on 7/1/17.
 */

public interface DiscoveryContract {


    interface View extends BaseView<Presenter> {

        void setupPager();

        void setupTabs();

        void onFavoritesChanged();
    }


    interface Presenter extends BasePresenter {

        void onFavoritesChanged();
    }
}
